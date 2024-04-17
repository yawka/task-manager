<template>

  <div class="container">

    <!-- --------------
      start main part
    --------------- -->

    <main>
      <h1>Dashbord</h1>

      <div class="insights">

        <!-- start task_completed -->
        <div class="task_completed">
          <div class="middle">

            <div class="left">
              <h1>4 Tasks</h1>
            </div>
            <div class="progress">
              <svg>
                <circle  r="21" cy="40" cx="40"></circle>
              </svg>
              <div class="number"><p>80%</p></div>
            </div>

          </div>

          <small>Completed</small>

          <div class="left_icon">
            <div class="icon-wrapper">
              <img src="/checking.png" alt="">
            </div>
          </div>

        </div>
        <!-- end task_completed -->
        <!-- start task_in_progress-->
        <div class="task_in_progress">
          <div class="middle">

            <div class="left">
              <h1>12 Tasks</h1>
            </div>

            <div class="progress">
              <svg>
                <circle  r="21" cy="40" cx="40"></circle>
              </svg>
              <div class="number"><p>80%</p></div>
            </div>
          </div>

          <small>In progress</small>

          <div class="left_icon">
            <div class="icon-wrapper">
              <img src="/checking.png" alt="">
            </div>
          </div>

        </div>
        <!-- end task_in_progress -->
      </div>

      <div class="scale_chart">
        <div>
          <h1>Overview</h1>
          <ScaleChart/>
        </div>
      </div>

      <div class="bottom">

        <div class="recent_tasks">
          <h1>Recent Tasks</h1>
          <div class="recent_task_1">
            <div class="icon-wrapper">
              <img src="/checklist.png" alt="">
            </div>
            <h2>CRUD operations for Task entity</h2>
            <p>Task Manager</p>
            <div class="transition_square">
              <a href="#">
                <span class="material-symbols-sharp">transit_enterexit</span>
              </a>
            </div>
          </div>

          <div class="line"></div>

          <div class="recent_task_2">
            <div class="icon-wrapper">
              <img src="/checklist.png" alt="">
            </div>
            <h2>Create new entity "Notification"</h2>
            <p>Task Manager</p>
            <div class="transition_square">
              <a href="#">
                <span class="material-symbols-sharp">transit_enterexit</span>
              </a>
            </div>
          </div>

        </div>
      </div>
    </main>
    <!------------------
       end main
      ------------------->

    <!----------------
      start right main
    ---------------------->
    <div class="right">

      <div class="doughnut_chart">
        <div>
          <h2>Summary</h2>
          <DoughnutChart ref="doughnutChartRef" />
        </div>
      </div>

      <div class="recent_updates">
        <div class="updates">
          <h2>Leader DashBoard</h2>
          <div class="update">
            <div class="profile-photo">
              <img src="/diversity_avatar_boy_man_beard_people_icon_159084.png" alt=""/>
            </div>
            <div class="message">
              <p><b>Stanislav Tun</b></p>
              <p>42 tasks completed</p>
              <div class="time_spent">
                <p class="hours">82H</p>
                <p>worked</p>
              </div>
            </div>
          </div>
          <div class="update">
            <div class="profile-photo">
              <img src="/diversity_avatar_boy_man_people_smiling_icon_159093.png" alt=""/>
            </div>
            <div class="message">
              <p><b>Artur Unusov</b></p>
              <p>32 tasks completed</p>
              <div class="time_spent">
                <p class="hours">30H</p>
                <p>worked</p>
              </div>
            </div>
          </div>
          <div class="update">
            <div class="profile-photo">
              <img src="/diversity_avatar_man_people_person_black_muslim_boy_icon_159082.png" alt=""/>
            </div>
            <div class="message">
              <p><b>Erkin Koshoev</b></p>
              <p>22 tasks completed</p>
              <div class="time_spent">
                <p class="hours">22H</p>
                <p>worked</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import { defineComponent, ref } from "vue";
import ScaleChart from "@/components/ScaleChart.vue";
import DoughnutChart from "@/components/DoughnutChart.vue";

export default defineComponent({
  components: { DoughnutChart, ScaleChart },
  setup() {
    const doughnutChartRef = ref(null);
    return { doughnutChartRef };
  },
});

</script>


<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,500;0,600;0,700;0,800;1,600&display=swap');

:root {
  --clr-primary: #7380ec;
  --clr-danger: #ff7782;
  --clr-success: #41f1b6;
  --clr-white: #fff;
  --clr-info-dark: #7d8da1;
  --clr-info-light: #dce1eb;
  --clr-dark: #363949;
  --clr-warnig: #ff4edc;
  --clr-light: rgba(132, 139, 200, 0.18);
  --clr-primary-variant: #111e88;
  --clr-dark-variant: #677483;
  --clr-color-background: #f6f6f9;

  --card-border-radius: 2rem;
  --border-radius-1: 0.4rem;
  --border-radius-2: 0.8rem;
  --border-radius-3: 1.2rem;

  --card-padding: 1.8rem;
  --padding-1: 1.2rem;
  --box-shadow: 0 2rem 3rem var(--clr-light);

}

/* dark theme */
.dark-theme-variables {
  --clr-color-background: #181a1e;
  --clr-white: #202528;
  --clr-light: rgba(0, 0, 0, 0.4);
  --clr-dark: #edeffd;
  --clr-dark-variant: #677483;
  --box-shadow: 0 2rem 3rem var(--clr-light);

  .headline-container {
    color: #fff;
  }
  h2 {
    color: #fff; /* Задайте желаемый цвет для h2 */
  }

  .v-divider {
    background-color: #555;
  }

  .v-text-field input {
    color: #ffffff;
    background-color: #333;
  }

  .v-card {
    background-color: #444;
    color: #ffffff;
  }

  .v-list {
    background-color: #444;
    color: #ffffff;

  }
}

.container  {
  display: grid;
  width: 96%;
  gap: 1.8rem;
  grid-template-columns: 14rem auto 16rem;
  margin: 0 auto;
}

a{
  color: var(--clr-dark);
}

h1{
  font-weight: 800;
  font-size: 2.8rem;
  width: 50%;
}
h2{
  font-size: 1.4rem;
}
h3{
  font-size: 0.87;
}
h4{
  font-weight: 0.8rem;
}
h5{
  font-size: 0.77rem;
}
small{
  font-size: 0.75rem;
}
.profile-photo img{
  width: 2.8rem;
  height: 2.8rem;
  overflow: hidden;
  border-radius: 50%;
}
.text-muted{
  color: var(--clr-info-dark);
}
p{
  color: var(--clr-dark-variant);
}
b{
  color: var(--clr-dark);
}

.danger {
  color: var(--clr-danger);
}
/* ----------------------------
--------- Main--------------
------------------------------ */

main{
  margin-top: 1.4rem;
  width: 0vh;
  background-color: red;
}

main .insights{
  display: grid;
  grid-template-columns: repeat(3,1fr);
  gap: 1.6rem;
  width: 545px;
}

main .insights>div{
  background-color: var(--clr-white);
  padding: var(--card-padding);
  border-radius: 15px;
  margin-top: 1rem;
  box-shadow: var(--box-shadow);
  transition: all .3s ease;
  width: 16.2rem;
  height: 5.5rem;
}

main .insights > div:hover{
  box-shadow: none;
}

main .insights>div span{
  background: coral;
  padding: 0.5rem;
  border-radius: 50%;
  color: var(--clr-white);
  font-size: 2rem;
}

main .insights .task_in_progress .left_icon .icon-wrapper {
  width: 50px;
  height: 50px;
  border-radius: 24%;
  overflow: hidden;
  display: flex;
  position: relative;
  justify-content: center;
  border: 2px solid #d5d5d5;
  top: -89px;
  left: -7%;
}

main .insights .task_in_progress .left_icon .icon-wrapper img{
  position: relative;
  top: 6px;
  left: 0.7%;
  width: 70%;
  height: 70%;
}

main .insights .task_completed .left_icon .icon-wrapper {
  width: 50px;
  height: 50px;
  border-radius: 24%;
  overflow: hidden;
  display: flex;
  position: relative;
  justify-content: center;
  border: 2px solid #d5d5d5;
  top: -88px;
  left: -7%;
}

main .insights .task_completed .left_icon .icon-wrapper img{
  position: relative;
  top: 7px;
  left: 0.7%;
  width: 69.9%;
  height: 70%;
}


main .scale_chart {
  display: grid;
  gap: 1.6rem;
  width: 34.2rem;
}

main .scale_chart > div {
  background-color: var(--clr-white);
  margin-top: 1.5rem;
  border-radius: 15px;
  box-shadow: var(--box-shadow);
  transition: all .3s ease;
  width: 34rem;
  height: 20rem;
}

main .scale_chart > div:hover{
  box-shadow: none;
}

main .scale_chart h1 {
  position: relative;
  top: 18px;
  left: 5%;
  font-size: 1rem;
}

main .scale_chart canvas {
  position: relative;
  margin-left: 25px;
  margin-right: 25px;
  top: 14px;
  margin-top: 1rem;
}

main .bottom{
  display: grid;
  gap: 1.6rem;
  width: 545px;
  margin-top: 4px;
  margin-bottom: 18px;
}

main .bottom > div{
  background-color: var(--clr-white);
  padding: var(--card-padding);
  border-radius: 15px;
  margin-top: 1rem;
  box-shadow: var(--box-shadow);
  transition: all .3s ease;
  width: 34.1rem;
  height: 15.3rem;
}

main .bottom > div:hover{
  box-shadow: none;
}

main .bottom h1 {
  position: relative;
  top: 0;
  font-size: 1.5rem;
}

main .bottom .recent_tasks .recent_task_1 h2 {
  position: relative;
  font-size: 1rem;
  top: 22px;
}



main .bottom .recent_tasks .recent_task_2 h2 {
  font-size: 1rem;
  left: 14.2%;
  position: relative;
  top: 35px;
}

main .bottom .recent_tasks .recent_task_1 p {
  position: relative;
  top: 42px;
  font-size: 0.74rem;
  right: 57%;
}


main .bottom .recent_tasks .recent_task_2 p {
  position: relative;
  top: 34px;
  left: 14.5%;
  font-size: 0.75rem;
}

main .bottom .recent_tasks .recent_task_1 .icon-wrapper {
  width: 50px;
  height: 50px;
  border-radius: 24%;
  overflow: hidden;
  display: flex;
  position: relative;
  justify-content: center;
  border: 2px solid #d5d5d5;
  top: 30px;
  right: 0;
}

.bottom .recent_tasks .recent_task_1 .icon-wrapper img{
  width: 45px;
  height: 45px;
  position: relative;
  bottom: 0;
  right: 0.39px;
}

main .bottom .recent_tasks .recent_task_2 .icon-wrapper {
  width: 50px;
  height: 50px;
  border-radius: 24%;
  overflow: hidden;
  display: flex;
  position: relative;
  justify-content: center;
  border: 2px solid #d5d5d5;
  top: 80px;
  right: 0;
}

.bottom .recent_tasks .recent_task_2 .icon-wrapper img{
  width: 45px;
  height: 45px;
  position: relative;
  bottom: 0;
  right: 0.39px;
}

main .recent_tasks .recent_task_1{
  height: 22px;
}

main .recent_tasks .line {
  position: relative;
  top: 64px;
  left: 10px;
  height: 2px;
  width: 96.5%;
  background-color: #c9c9c9;
  border-radius: 10px;
}

.bottom .recent_tasks .recent_task_1 {
  display: flex;
  align-items: center;
  justify-content: start;
  gap: 20px;
}

.bottom .recent_tasks .recent_task_1 .transition_square {
  width: 34px;
  height: 34px;
  border-radius: 24%;
  overflow: hidden;
  display: flex;
  position: relative;
  justify-content: center;
  border: 2px solid #d5d5d5;
  top: 40px;
}

.bottom .recent_tasks .recent_task_2 .transition_square {
  width: 34px;
  height: 34px;
  border-radius: 24%;
  overflow: hidden;
  display: flex;
  position: relative;
  justify-content: center;
  border: 2px solid #d5d5d5;
  top: 7px;
  left: 93%;
}

.recent_task_1 .transition_square .material-symbols-sharp {
  display: inline-block;
  transform: rotate(90deg);
  position: relative;
  top: 3px;
  transition: all .2s ease;
}

.recent_task_1 .transition_square .material-symbols-sharp:hover {
  transform: rotate(180deg);
}

.recent_task_2 .transition_square .material-symbols-sharp {
  display: inline-block;
  transform: rotate(90deg);
  position: relative;
  top: 3.2px;
  transition: all .2s ease;
}

.recent_task_2 .transition_square .material-symbols-sharp:hover {
  transform: rotate(180deg);
}

main .insights > div.expenses span{
  background: var(--clr-danger);
}

main .insights > div.income span{
  background: var(--clr-success);
}

main .insights > div .middle{
  display: flex;
  align-items: center;
  justify-content: space-between;
}

main .insights > div .middle h1{
  font-size: 1.1rem;
  position: relative;
  width: 100%;
  top: -20px;
  left: 63%;
}

main .insights .task_completed small{
  font-size: 0.8rem;
  position: relative;
  top: -42px;
  left: 22%;
}

main .insights .task_in_progress small{
  font-size: 0.8rem;
  position: relative;
  top: -43px;
  left: 23.5%;
}

main h1{
  color: var(--clr-dark);
}
main .insights h1{
  color: var(--clr-dark);
}
main .insights h3{
  color: var(--clr-dark);
}
main .insights p{
  color: var(--clr-dark);
}
main .insights h3{

  color: var(--clr-dark);


}

main .insights .progress{
  position: relative;
  top: -25px;
  height: 60px;
  width: 55px;
  border-radius: 50px;
}

main .insights svg{
  height: 150px;
  width: 150px;
  position: absolute;
  top: 0;
}

main .insights svg circle{
  fill: none;
  stroke: var(--clr-primary);
  transform: rotate(270,80,80);
  stroke-width: 5;
}
main .insights .sales svg circle{
  stroke-dashoffset: 0;
  stroke-dasharray: 150;
}

main .insights .expenses svg circle{
  stroke-dashoffset: 10;
  stroke-dasharray: 150;
}
main .insights .sales svg circle{
  stroke-dashoffset: 0;
  stroke-dasharray: 150;
}
main .insights .income svg circle{
  stroke: var(--clr-success);
}

main .insights .progress .number{
  position: absolute;
  top: 16%;
  left: 24%;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

/*********************
Right Side
**********************/


.right {
  margin-top: 1.4rem;
}

.right h2{
  color: var(--clr-dark);
}

.right .top{
  display: flex;
  position: relative;
  right: 50%;
  justify-content: start;
  width: 100vh;
  height: 30px;
}

.right .top button{
  display: none;
}

.right .theme-toggler{
  background: var(--clr-white);
  display: flex;
  justify-content: space-between;
  height: 1.6rem;
  width: 4.2rem;
  cursor: pointer;
  border-radius: var(--border-radius-1);
  margin-right: 40px;
  top: 50px;
}

.right .top .project-button {
  background-color: #007bff;
  color: white;
  border: none;
  font-size: 16px;
  cursor: pointer;
  position: relative;
  bottom: 10px;
  left: 40px;
  height: 24px;
  top: 1px;
  border-radius: 20px;
  width: 76px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.right .top .project-button .triangle {
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 5px 0 5px 8.7px;
  border-color: transparent transparent transparent white;
  margin-left: 5px;
  display: flex;
}

.right .theme-toggler span{
  font-size: 1.2rem;
  width: 50%;
  height: 100%;
  display: flex;
  position: relative;
  align-items: center;
  justify-content: center;
}

.right .theme-toggler span.active{
  background: var(--clr-primary);
  color: #fff;
}

.right .top .profile{
  display: flex;
  gap: 1rem;
  height: 50px;
  text-align: right;
  position: relative;
  left: 12%;
}


.right .top .profile img{
  position: relative;
  bottom: 6px;
}

.right  .info h3{
  color: var(--clr-dark);
}
.right  .item h3{
  color: var(--clr-dark);
}

/* recent update */

.right .recent_updates{
  position: relative;
  bottom: 60px;
  margin-left: -20px;
  margin-right: 90px;
  width: 100%;
  height: 40%;
}

.right .recent_updates .updates{
  background-color: var(--clr-white);
  border-radius: var(--card-border-radius);
  position: relative;
  top: 171px;
  left: 77%;
  height: 15.2rem;
  width: 25.4rem;
  transition: all .3s ease;
  margin-bottom: 44px;
}

.right .recent_updates .updates h2{
  font-size: 1.2rem;
  position: relative;
  margin-bottom: 31px;
  left: 28%;
  top: 10px;
}

.right .recent_updates .updates .update{
  margin-top: -15px;
  margin-left: 0.5rem;
  top: 25px;
  left: 5%;
}

.right .recent_updates .updates .update p{
  position: relative;
  top: 7px;
  left: 1%;
}

.right .recent_updates .updates .update .time_spent {
  position: relative;
  left: 80%;
  bottom: 31px;
}

.right .recent_updates .updates .update .time_spent .hours {
  position: relative;
  left: 6%;
}

.right .recent_updates .updates:hover{
  box-shadow: none;
}

.right .recent_updates .update{
  display: grid;
  grid-template-columns: 2.6rem auto;
  gap: 1rem;
  margin-bottom: 1rem;
}

.right .doughnut_chart h2{
  position: relative;
  top: 0;
  font-size: 1.2rem;
  margin-bottom: 0.6rem;
}

.right .doughnut_chart{
  background-color: var(--clr-white);
  padding: var(--card-padding);
  border-radius: var(--card-border-radius);
  box-shadow: var(--box-shadow);
  transition: all .3s ease;
  position: relative;
  top: 85px;
  left: 73%;
  width: 85%;
  height: 53.5%;
}

.right .doughnut_chart canvas {
  margin-right: 120px;
  position: relative;
  left: 17%;
}

.right .doughnut_chart:hover{
  box-shadow: none;
}


/* **********8
MEDIA QUERY
****************/

@media screen and (max-width:1200px) {
  .container{
    width: 94%;
    grid-template-columns: 7rem auto 14rem;
  }
  aside .sidebar h3{
    display: none;
  }
  aside .sidebar a{
    width: 5.6rem;
  }
  aside .sidebar a:last-child{
    position: relative;
    margin-top: 1.8rem;
  }
  main .insights{
    display: grid;
    grid-template-columns: repeat(1,1fr);
  }
}

/* ****************
MEDIA QUERY PHONE
************************/

@media screen and (max-width:768px) {
  .container{
    width: 100%;
    grid-template-columns: repeat(1,1fr);

  }

  aside{
    position: fixed;
    width: 18rem;
    z-index: 3;
    height: 100vh;
    background-color: var(--clr-white);
    display: none;
    left: -110px;
    animation:  menuLeft .3s ease forwards;
  }

  @keyframes menuLeft {
    to{
      left: 0;
    }
  }



  aside .logo h2{
    display: inline;
  }
  aside .sidebar h3{
    display: inline;
  }
  aside .sidebar a{
    width: 100%;
    height: 3.4rem;
  }
  aside .top div.close  span{
    display: inline;
    position: absolute;
    right: 0;
    margin-right:30px;
    font-size: 35px;
    cursor: pointer;
  }

  .right .top{
    position: fixed;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 0.8rem;
    background: var(--clr-white);
    height: 4.6rem;
    width: 100%;
    z-index: 2;
    box-shadow: 0 1rem 1rem var(--clr-light);
    margin: 0;
  }


  main .insights{
    display: grid;
    grid-template-columns: repeat(1,1fr);
    gap: 1.6rem;
    padding: 40px;
  }

  .right .profile{
    position: absolute;
    left: 70%;
  }

  .right .top .theme-toggler{
    width: 4.4rem;
    position: absolute;
    left: 50%;
  }
  .right .profile .info{
    display: none;
  }

  .right .top button{
    display: inline-block;
    background: transparent;
    cursor: pointer;
    color: var(--clr-dark);
    position: absolute;
    left: 1rem;

  }

}
</style>



