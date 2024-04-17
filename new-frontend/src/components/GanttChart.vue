<template>
  <div class="main_container">
    <div class="gantt-container">
      <h1>Gantt Chart</h1>
      <div>
        <ejs-gantt
            ref="gantt"
            id="GanttContainer"
            :dataSource="dataSource"
            :taskFields="taskFields"
            :height="height"
            :width="width"
            :editDialogFields="editDialogFields"
            :editSettings="editSettings"
            :toolbar="toolbar"
            :resourceFields="resourceFields"
            :resources="resources"
            :labelSettings="labelSettings"
            :selectionSettings="selectionSettings"
            :rowHeight="rowHeight"
            :rowDrop="rowDrop"
            :allowRowDragAndDrop="true"
            :columns="columns"
            :allowFiltering="true"
            :filterSettings="filterSettings"
            :splitterSettings="splitterSettings"
            :allowExcelExport="true"
            :includeWeekend="true"
            :toolbarClick="toolbarBtnClk"
            @actionComplete='onActionBegin'
        ></ejs-gantt>
        <div class="dialogue-container" v-if="showDialog">
          <div class="bars">
            <h2>New Task</h2>
            <div class="sections">
              <h3 v-for="(section, index) in sectionTitles" :key="index" @click="moveIndicator(index)">{{
                  section
                }}</h3>
              <div class="indicator" :style="{ width: indicatorWidth + 'px', left: indicatorLeft + 'px' }"></div>
            </div>
          </div>

          <div v-if="activeSectionIndex === 0">
            <div class="id">
              <v-text-field
                  class="custom-background"
                  v-model="id"
                  label="ID"
                  :style="{ backgroundColor: '#ffffff' }"
                  variant="underlined"
              ></v-text-field>
            </div>

            <div class="name">
              <v-text-field
                  class="custom-background"
                  v-model="name"
                  label="Name"
                  :style="{ backgroundColor: '#ffffff' }"
                  variant="underlined"
              ></v-text-field>
            </div>

            <div class="start_date">
              <label for="startDatePicker">Start Date</label>
              <ejs-datepicker
                  id="datepicker"
                  :placeholder="waterMarkText"
                  :renderDayCell="disableDates"
                  v-model="startDate"
              >
              </ejs-datepicker>
            </div>

            <div class="end_date">
              <label for="endDatePicker">End Date</label>
              <ejs-datepicker
                  id="datepicker"
                  :placeholder="waterMarkText"
                  :renderDayCell="disableDates"
                  v-model="endDate"
              >
              </ejs-datepicker>
            </div>

            <div class="duration">
              <v-text-field
                  class="custom-background"
                  v-model="duration"
                  label="Duration"
                  :style="{ backgroundColor: '#ffffff' }"
                  variant="underlined"
              ></v-text-field>
            </div>

            <div class="progress">
              <v-text-field
                  class="custom-background"
                  v-model="progress"
                  label="Progress"
                  :style="{ backgroundColor: '#ffffff' }"
                  variant="underlined"
              ></v-text-field>
            </div>

            <div class="save">
              <ejs-button @click="saveTask">Save</ejs-button>
            </div>

            <div class="cancel">
              <ejs-button @click="closeDialog">Cancel</ejs-button>
            </div>
          </div>

          <div v-if="activeSectionIndex === 1">
            <ejs-grid class="grid"
                      :dataSource='dataSource'
                      :allowFiltering='true'
                      :filterSettings='filterOptions'
                      :height='320'
                      @rowSelected="onRowSelected">
              <e-columns>
                <e-column type='checkbox' width='50'></e-column>
                <e-column field='taskId' headerText='ID' textAlign='Left' width=50></e-column>
                <e-column field='taskName' headerText='Title' textAlign='Center' width=120></e-column>
                <e-column field='Creator.username' headerText='Creator' textAlign='Center' format='C2' width=90></e-column>
              </e-columns>
            </ejs-grid>
          </div>

          <div v-if="activeSectionIndex === 2">
            <ejs-grid class="grid"
                      :dataSource='users'
                      :allowFiltering='true'
                      :filterSettings='filterOptions'
                      :height='320'
                      @rowSelected="onUserRowSelected">
              <e-columns>
                <e-column type='checkbox' width='50'></e-column>
                <e-column field='userId' headerText='ID' textAlign='Left' width=50></e-column>
                <e-column field='username' headerText='Name' textAlign='Center' width=120></e-column>
                <e-column field='position' headerText='Position' textAlign='Center' format='C2' width=90></e-column>
              </e-columns>
            </ejs-grid>
          </div>

          <div v-if="activeSectionIndex === 3">
            <div class="description_section">
              <ejs-richtexteditor ref="defaultRTE"
                                  v-model="description"
                                  :height="330"
                                  :width="557"
              >
              </ejs-richtexteditor>
            </div>

          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {createApp} from "vue";
import {
  DayMarkers,
  Edit,
  ExcelExport,
  Filter,
  GanttComponent,
  GanttPlugin,
  RowDD,
  Selection,
  Toolbar as GanttToolbar,
} from "@syncfusion/ej2-vue-gantt";
import {
  Count,
  HtmlEditor,
  Image,
  Link,
  QuickToolbar,
  RichTextEditorComponent,
  Toolbar
} from '@syncfusion/ej2-vue-richtexteditor';
import TaskDataService from "@/services/TaskDataService";
import {DatePickerComponent} from "@syncfusion/ej2-vue-calendars";
import {ButtonComponent} from '@syncfusion/ej2-vue-buttons';
import {enableRipple} from '@syncfusion/ej2-base';
import {
  ColumnDirective,
  ColumnsDirective,
  Filter as GridFilter,
  GridComponent,
  GridPlugin
} from "@syncfusion/ej2-vue-grids";
import {DropDownListPlugin} from '@syncfusion/ej2-vue-dropdowns';
import UserDataService from "@/services/UserDataService";


enableRipple(true);


createApp().use(GanttPlugin).use(GridPlugin).use(DropDownListPlugin);

export default {

  name: "App",
  components: {
    "ejs-gantt": GanttComponent,
    'ejs-datepicker': DatePickerComponent,
    'ejs-button': ButtonComponent,
    'ejs-richtexteditor': RichTextEditorComponent,
    'ejs-grid' : GridComponent,
    'e-columns' : ColumnsDirective,
    'e-column' : ColumnDirective
  },
  data: function () {
    return {
      waterMarkText: "Choose a date",
      startDate: new Date(),
      endDate: new Date(),

      sectionTitles: ['GENERAL', 'DEPENDENCY', 'RESOURCES', 'NOTES'],
      indicatorWidth: 0,
      indicatorLeft: 0,
      tabs: null,
      id: '',
      name: 'New Task',
      due: null,
      progress: '0',
      description: null,
      activeSectionIndex: 0,

      dataSource: [],

      disabledDates: [],

      users: [],

      selectedTask: null,

      selectedUser: null,

      showDialog: false,

      projectData: null,

      filterOptions : { type: 'CheckBox' },


      height: "100%",
      width: "100%",

      taskFields: {
        id: 'taskId',
        name: "taskName",
        startDate: "startDate",
        endDate: "endDate",
        duration: "duration",
        progress: "progress",
        dependency: "parentTask",
        parentID: "parentTaskId",
        notes: "description",
        resourceInfo: "executorUserId",
      },

      rowHeight: 40,

      columns: [
        {
          field: "taskId",
          headerText: "ID",
          textAlign: "Left",
          width: "82",
        },
        {field: "taskName", headerText: "Task Name", width: "250"},
        {field: "startDate", headerText: "Start Date", width: "150"},
        {field: "endDate", headerText: "End Date", width: "150"},
        {field: "duration", headerText: "Duration", width: "150"},
        {field: "userName", headerText: "Resources", width: "250"},
      ],

      editDialogFields: [
        {type: 'General', headerText: 'General'},
        {type: 'Dependency'},
        {type: 'Resources'},
        {type: 'Notes'},
      ],

      resourceFields: {
        id: "userId",
        name: "username",
      },

      resources: this.users,

      editSettings: {
        allowAdding: true,
        allowEditing: true,
        mode: "Dialog",
        allowTaskbarEditing: true,
        allowDeleting: true,
        showDeleteConfirmDialog: true,
      },

      toolbar: ["Add", "Delete", "ExcelExport", "CsvExport"],

      labelSettings: {
        rightLabel: "resources",
        taskLabel: '${progress}%'
      },

      selectionSettings: {
        mode: "Row",
        type: "Multiple",
      },

      splitterSettings: {
        columnIndex: 3,
      },

      filterSettings: {
        type: "Excel",
      },
    };
  },
  provide: {
    gantt: [DayMarkers, Edit, GanttToolbar, Selection, RowDD, Filter, ExcelExport,],
    richtexteditor:[Toolbar, Link, Image, Count, HtmlEditor, QuickToolbar],
    grid: [GridFilter]
  },

  watch: {
    startDate(newVal) {
      const today = new Date(new Date().setHours(0, 0, 0, 0));
      if (newVal < today) {
        this.startDate = today;
      } else if (this.startDate > this.endDate) {
        this.endDate = new Date(this.startDate);
      }
    },
    endDate() {
      if (this.endDate < this.startDate) {
        this.startDate = new Date(this.endDate);
      }
    }
  },

  computed: {
    duration() {
      if (!this.startDate || !this.endDate) {
        return 0;
      }

      const diffInMs = this.endDate - this.startDate;
      const diffInDays = diffInMs / (1000 * 60 * 60 * 24);

      return Math.max(0, Math.round(diffInDays)) + 1;
    }
  },

  mounted() {
    const projectData = sessionStorage.getItem('currentProject');
    if (projectData) {
      this.projectData = JSON.parse(projectData);
      this.checkProjectConstraints();
    }

    let projectId = this.$route.query.projectId;
    this.projectId = projectId;
    this.fetchProjectData(projectId);
    this.fetchUsers(projectId);

    this.$nextTick(() => {
      this.tabs = this.$el.querySelectorAll('.sections h3');
      if (this.tabs.length > 0) {
        this.moveIndicator(0);
      }
    });
  },

  methods: {

    moveIndicator(index) {
      this.activeSectionIndex = index;
      const tab = this.tabs[index];
      this.indicatorWidth = tab.offsetWidth;
      this.indicatorLeft = tab.offsetLeft;
    },

    onRowSelected(e) {
      this.selectedTask = e.data;
    },

    onUserRowSelected(e) {
      this.selectedUser = e.data;
    },

    disableDates(args) {
      const dateStr = args.date.toISOString().split('T')[0];
      if (this.disabledDates.includes(dateStr)) {
        args.isDisabled = true;
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

    onActionBegin(args) {
      console.log(args)
      if (args.requestType === 'rowDropped') {
        const droppedTaskData = args.data;

        const targetRow = args.targetRow;
        console.log(`Task was dropped.`, droppedTaskData, targetRow);

      }
      if (args.requestType === 'openEditDialog' || args.action === 'OpenDialog') {
        args.cancel = true;
        this.openDialog();
      } else if (args.action === 'DialogEditing' || args.action === 'TaskbarEditing') {
        this.updateTask(args.data.taskId, args.data)
      } else if (args.requestType === 'delete') {
        if (Array.isArray(args.data)) {
          args.data.forEach(task => {
            this.deleteTask(task.taskId);
          });
        } else {
          this.deleteTask(args.data.taskId);
        }
      }
    },

    saveTask() {
      const taskData = {
        taskName: this.name,
        description: this.description,
        startDate: this.startDate,
        duration: this.duration,
        endDate: this.endDate,
        progress: this.progress,
        parentTask: this.selectedTask,
        executor: this.selectedUser
      };

      console.log(taskData)

      this.createTask(taskData).then(() => {
        this.showDialog = false;
      }).catch(error => {
        console.error("Ошибка при создании задачи:", error);
      });
    },

    async createTask(taskData) {
      const taskRequestDTO = {
        taskName: taskData.taskName,
        description: taskData.description,
        startDate: taskData.startDate,
        duration: taskData.duration,
        endDate: taskData.endDate,
        progress: taskData.progress,
        parentTask: this.selectedTask,
        executor: this.selectedUser,
        project: this.projectData,
      };

      console.log(taskData.selectedTask)
      console.log(taskRequestDTO)
      try {
        const response = await TaskDataService.create(taskRequestDTO);
        if (response.status === 201) {
          console.log('Task created successfully', response.data);
          await this.fetchProjectData(this.projectData.projectId)
        } else {
          console.error('Failed to create task', response);
        }
      } catch (error) {
        console.error('Error creating task:', error);
      }
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
        userId: 1,
      };
      try {
        const response = await TaskDataService.update(taskId, taskRequestDTO);
        if (response.status === 200) {
          console.log('Task updated successfully', response.data);
          await this.fetchProjectData(this.projectData.projectId)
        } else {
          console.error('Failed to update task', response);
        }
      } catch (error) {
        console.error('Error updating task:', error);
      }

    },

    async deleteTask(taskId) {
      try {
        const response = await TaskDataService.delete(taskId)
        if (response.status === 200) {
          console.log('Task deleted successfully', response.data);
        } else {
          console.error('Failed to delete task', response);
        }
      } catch (error) {
        console.error('Error deleting task:', error);
      }
    },

    async fetchProjectData(projectId) {
      try {
        const response = await TaskDataService.getAll(projectId);
        await this.fetchDisabledDates(projectId);
        this.dataSource = response.data.map(task => ({
          ...task,
          executorUserId: task.executor ? task.executor.userId : null,
        }));
        await this.fetchDisabledDates(this.projectId);
      } catch (error) {
        console.error('Error fetching project tasks:', error);
      }
    },


    async fetchUsers(id) {
      try {
        const response = await UserDataService.getUsersByProject(id);
        this.users = response.data;
        console.log(response.data)
      } catch (error) {
        console.error('Error fetching project tasks:', error);
      }
    },

    rowDrop: function (args) {
      if (args.dropPosition === "middleSegment") {
        args.cancel = true;
        this.$refs.gantt.reorderRows([args.fromIndex], args.dropIndex, "above");
      }
    },

    toolbarBtnClk(args) {
      let ganttObj = this.$refs.gantt.ej2Instances;
      if (args.item.text == 'Excel export') {
        ganttObj.excelExport({
          fileName: 'projectData.xlsx',
          theme: {
            header: {fontColor: "#de3600"},
            record: {fontColor: "#000000"}
          },
          header: {
            headerRows: 1,
            rows: [{
              cells: [{
                colSpan: 4,
                value: 'Project Time Tracking Report',
                style: {fontSize: 20, hAlign: 'Center'}
              }]
            }]
          },
        })
      } else if (args.item.text == 'CSV export') {
        ganttObj.csvExport()
      }
    },

    openDialog() {
      this.showDialog = true;
      this.$nextTick(() => {
        this.tabs = this.$el.querySelectorAll('.sections h3');
        if (this.tabs.length > 0) {
          this.moveIndicator(0);
        }
      });
    },

    closeDialog() {
      this.showDialog = false;
    },

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
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,500;0,600;0,700;0,800;1,600&display=swap');

:root {
  --clr-light: rgba(132, 139, 200, 0.18);
}

.main_container {
  display: grid;
  width: 96%;
  gap: 1.8rem;
  grid-template-columns: 14rem auto 16rem;
}

.main_container .gantt-container {
  margin-top: 27px;
  height: 10.5vh;
  width: 70vw;
}

.main_container h1 {
  font-weight: 800;
  font-size: 2.6rem;
  width: 100%;
}

.main_container .gantt-container {
  display: grid;
  gap: 1.6rem;
  width: 65rem;
}


.gantt-container > div {
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2rem 3rem var(--clr-light);
  transition: all .3s ease;
  width: 64.8rem;
  height: 42.7rem;
}

.e-gantt {
  border-radius: 15px;
  overflow: hidden;
}


:root {
  --clr-light: rgba(132, 139, 200, 0.18);
}

.dialogue-container {
  background-color: #ffffff;
  border: 2px solid #000000;
  border-radius: 15px;
  box-shadow: 0 2rem 3rem var(--clr-light);
  transition: all .3s ease;
  margin-left: 400px;
  position: absolute;
  bottom: 12%;
  right: 21%;
  width: 35rem;
  height: 25rem;
  overflow: hidden;
  z-index: 1050;
}

.bars {
  height: 95px;
  width: 35rem;
  position: relative;
  bottom: 8px;
  margin-bottom: 12px;
  right: 2px;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  background-color: #282ecc;
  border-top: 2px solid #000000;
  border-right: 2px solid #000000;
  border-left: 2px solid #000000;
  z-index: 1050;
}

.main_container .bars h2 {
  font-size: 1.4rem;
  position: relative;
  top: 15px;
  margin-left: 16px;
}

.main_container .bars .sections {
  display: flex;
  gap: 1.6rem;
  margin-top: 34px;
  margin-left: 14px;
  position: relative;
}

.main_container .bars .sections h3 {
  font-size: 1rem;
}

.main_container .bars .sections .indicator {
  height: 2.3px;
  right: 1px;
  width: 0;
  background-color: black;
  position: absolute;
  top: 22.5px;
  transition: all .3s ease;
}

.main_container .id {
  margin-left: 15px;
  width: 235px;
  display: flex;
}

.main_container .name {
  margin-left: 15px;
  width: 235px;
  position: relative;
  left: 290px;
  bottom: 69.9px;
}

.main_container .start_date {
  margin-left: 15px;
  width: 235px;
  position: relative;
  bottom: 50.2px;
}

.main_container .end_date {
  margin-left: 15px;
  width: 235px;
  position: relative;
  bottom: 101px;
  left: 290px;
}

.main_container .duration {
  margin-left: 15px;
  width: 235px;
  position: relative;
  bottom: 72.5px;
}

.main_container .progress {
  margin-left: 15px;
  width: 235px;
  position: relative;
  bottom: 142px;
  left: 290px;
}

.main_container .save {
  position: relative;
  bottom: 130px;
  left: 360px;
}

.main_container .cancel {
  position: relative;
  bottom: 158px;
  left: 460px;
}

.main_container .e-btn {
  width: 80px;
}

.description_section {
  position: relative;
  bottom: 24px;
}

.grid {
  position: relative;
  bottom: 20px;
}

@import "~@syncfusion/ej2-base/styles/material.css";
@import "~@syncfusion/ej2-buttons/styles/material.css";
@import "~@syncfusion/ej2-calendars/styles/material.css";
@import "~@syncfusion/ej2-dropdowns/styles/material.css";
@import "~@syncfusion/ej2-inputs/styles/material.css";
@import "~@syncfusion/ej2-navigations/styles/material.css";
@import "~@syncfusion/ej2-popups/styles/material.css";
@import "~@syncfusion/ej2-splitbuttons/styles/material.css";
@import "~@syncfusion/ej2-layouts/styles/material.css";
@import "~@syncfusion/ej2-grids/styles/material.css";
@import "~@syncfusion/ej2-treegrid/styles/material.css";
@import "~@syncfusion/ej2-vue-gantt/styles/material.css";
@import url("https://cdn.syncfusion.com/ej2/material.css");

</style>
