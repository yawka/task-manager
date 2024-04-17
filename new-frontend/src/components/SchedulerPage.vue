<script>
import {defineComponent} from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import TaskDataService from "@/services/TaskDataService";
import {toast} from "vue3-toastify";
import 'vue3-toastify/dist/index.css';

export default defineComponent({
  components: {
    FullCalendar,
  },
  data() {
    return {
      projectData: null,
      dataSource: [],
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        events: [], // This line replaces `initialEvents: INITIAL_EVENTS`
        height: 600,
        weekends: true,
        // select: this.handleDateSelect,
        // eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        eventResize: this.handleEventResize,
        eventDrop: this.handleEventDrop,
        editable: true, // Add this line
      },

      currentEvents: [],
    }
  },
  mounted() {
    let projectId = localStorage.getItem('projectId');
    console.log('projectId from localStorage:', projectId); // Debug line
    this.projectId = projectId;
    this.fetchProjectData(projectId);
    const projectData = sessionStorage.getItem('currentProject');
    if (projectData) {
      this.projectData = JSON.parse(projectData);
      this.checkProjectConstraints();
    }
  },

  watch: {
    'calendarOptions.events': {
      immediate: true,
      handler() {
        this.$nextTick(() => {
          if (this.$refs.calendar) {
            this.$refs.calendar.getApi().render();
          }
        });
      },
    },
  },

  methods: {

    checkProjectConstraints() {
      console.log(this.projectData)
      this.fetchDisabledDates(3);
    },

    async fetchDisabledDates(projectId) {
      try {
        const response = await TaskDataService.getUniqueDates(projectId)
        if (response.status === 200) {
          this.fillDisabledDates(response.data);
        } else {
          console.error('Failed to fetch disabled dates');
        }
      } catch (error) {
        console.error('Error fetching disabled dates:', error);
      }
    },

    fillDisabledDates(dateRanges) {
      this.disabledDates = [];
      dateRanges.forEach(range => {
        let currentDate = new Date(range.startDate);
        let endDate = new Date(range.endDate);

        while (currentDate <= endDate) {
          this.disabledDates.push(currentDate.toISOString().split('T')[0]);
          currentDate.setDate(currentDate.getDate() + 1);
        }
      });
    },


    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends // update a property
    },


    async handleEventResize(info) {
      let event = info.event;
      console.log(`Event ${event.title} end time changed to ${event.endStr}`);
      // Find the task in the dataSource
      let task = this.dataSource.find(task => task.taskName === event.title);
      let startDate = new Date(event.start);
      let endDate = new Date(event.end);
      let duration = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));

      if (task) {
        let taskData = {
          taskName: task.taskName,
          description: task.description,
          startDate: this.convertDate(event.start),
          endDate: this.convertDate(event.end),
          duration: duration,
          progress: task.progress,
        };
        await this.updateTask(task.taskId, taskData);
      }
    },

    async handleEventDrop(info) {
      let event = info.event;
      console.log(`Event ${event.title} start time changed to ${event.startStr}, end time changed to ${event.endStr}`);
      // Find the task in the dataSource
      let task = this.dataSource.find(task => task.taskName === event.title);
      if (task) {
        let taskData = {
          taskName: task.taskName,
          description: task.description,
          startDate: this.convertDate(event.start),
          endDate: this.convertDate(event.end),
          duration: task.duration,
          progress: task.progress,
        };
        await this.updateTask(task.taskId, taskData);
      }
    },

    convertDate(date) {
      let newDate = new Date(date);
      let year = newDate.getFullYear();
      let month = newDate.getMonth() + 1;
      let day = newDate.getDate();
      let hours = newDate.getHours();
      let minutes = newDate.getMinutes();
      let seconds = newDate.getSeconds();
      return `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}T${hours < 10 ? '0' + hours : hours}:${minutes < 10 ? '0' + minutes : minutes}:${seconds < 10 ? '0' + seconds : seconds}`;
    },


    async updateTask(taskId, taskData) {
      const taskRequestDTO = {
        taskName: taskData.taskName,
        description: taskData.description,
        startDate: taskData.startDate,
        endDate: taskData.endDate,
        duration: taskData.duration,
        progress: taskData.progress,
        parentTaskId: taskData.parentTask,
        project: this.projectData,
      };
      try {
        const response = await TaskDataService.update(taskId, taskRequestDTO);
        if (response.status === 200) {
          console.log('Task updated successfully', response.data);
        } else {
          console.error('Failed to update task', response);
        }
      } catch (error) {
        console.error('Error updating task:', error);
        toast.error(error.response.data.error);
      }
    },
    // handleEventClick(clickInfo) {
    //   if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
    //     clickInfo.event.remove()
    //   }
    // },

    handleEvents(events) {
      this.currentEvents = events
    },

    async fetchProjectData(projectId) {
      try {
        const response = await TaskDataService.getAll(projectId);
        this.dataSource = response.data;
        console.log(response.data)

        let events = this.dataSource.map(task => {
          let startDate = new Date(task.startDate);
          let endDate = new Date(task.endDate);

          let startYear = startDate.getFullYear();
          let startMonth = startDate.getMonth() + 1;
          let startDay = startDate.getDate();

          let endYear = endDate.getFullYear();
          let endMonth = endDate.getMonth() + 1;
          let endDay = endDate.getDate();

          return {
            title: task.taskName,
            start: `${startYear}-${startMonth < 10 ? '0' + startMonth : startMonth}-${startDay < 10 ? '0' + startDay : startDay}`,
            end: `${endYear}-${endMonth < 10 ? '0' + endMonth : endMonth}-${endDay < 10 ? '0' + endDay : endDay}`
          }
        });

        this.calendarOptions.events = events; // Update the events in calendarOptions
        console.log(this.calendarOptions);
      } catch (error) {
        console.error('Error fetching project tasks:', error);
      }
    },
  },

})

</script>

<template>
  <div class='demo-app'>
    <div class='demo-app-sidebar'>
      <div class='demo-app-sidebar-section'>
      </div>
      <div class='demo-app-sidebar-section'>
        <label>
          <input
              type='checkbox'
              :checked='calendarOptions.weekends'
              @change='handleWeekendsToggle'
          />
        </label>
      </div>
      <div class='demo-app-sidebar-section'>
        <h2>All Events ({{ currentEvents.length }})</h2>
        <ul>
          <li v-for='event in currentEvents' :key='event.id'>
            <b>{{ event.startStr }}</b>
            <i>{{ event.title }}</i>
          </li>
        </ul>
      </div>
    </div>
    <div class='demo-app-main'>
      <FullCalendar
          ref="calendar"
          class='demo-app-calendar'
          :options='calendarOptions'
      >
        <template v-slot:eventContent='arg'>
          <b>{{ arg.timeText }}</b>
          <i>{{ arg.event.title }}</i>
        </template>
      </FullCalendar>
    </div>
  </div>
</template>

<style lang='css'>

h2 {
  margin: 0;
  font-size: 16px;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b { /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-height: 100%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.demo-app-sidebar {
  width: 300px;
  line-height: 1.5;
  background: #eaf9ff;
  border-right: 1px solid #d3e2e8;
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-sidebar-section ul li b,
.demo-app-sidebar-section ul li i {
  display: inline-block;
  width: 125px; /* Adjust as desired */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  height: 80%;
  width: 400%; /* Set width to a percentage */
  max-width: unset;
  max-height: unset;
  margin: 0 auto;
}


</style>