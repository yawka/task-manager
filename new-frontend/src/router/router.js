import { createRouter, createWebHistory } from "vue-router";
import ProjectsPage from "@/components/ProjectsPage.vue";
import LoginPage from "@/components/LoginPage.vue";
import UsersPage from "@/components/UsersComponent.vue";
import ProfileUser from "@/components/ProfileUser.vue";
import GanttChart from "@/components/GanttChart.vue";
import DashboardComponent from "@/components/DashboardComponent.vue";
import UserGroupComponent from "@/components/UserGroupComponent.vue";
import TasksComponent from "@/components/TasksComponent.vue";
import RegistrationPage from "@/components/RegistrationPage.vue";
import SchedulerPage from "@/components/SchedulerPage.vue";
import VerifyEmailPage from "@/components/VerifyEmailPage.vue";
import ResetPasswordPage from "@/components/ResetPasswordPage.vue";
import MyProfile from "@/components/MyProfile.vue";


const routes = [

  {
    path: '/',
    redirect: '/login'
  },

  {
    path: "/dashboard",
    name: "DashBoard",
    component: DashboardComponent,
  },

  {
    path: "/projects",
    name: "ProjectsPage",
    component: ProjectsPage,
  },

  {
    path: "/gantt-chart",
    name: "GanttChart",
    component: GanttChart,
  },

  {
    path: "/access-rights",
    name: "UsersComponent",
    component: UsersPage,
  },

  {
    path: "/profile",
    name: "ProfileUser",
    component: ProfileUser,
  },

  {
    path: "/login",
    name: "LoginPage",
    component: LoginPage,
  },

  {
    path: "/users",
    name: "UsersPage",
    component: UsersPage,
  },

  {
    path: "/tasks",
    name: "TasksComponent",
    component: TasksComponent,
  },

  {
    path: "/signup",
    name: "RegistrationPage",
    component: RegistrationPage,
  },

  {
    path: "/verify-email",
    name: "VerifyEmailPage",
    component: VerifyEmailPage,
  },

  {
    path: "/reset-password",
    name: "ResetPasswordPage",
    component: ResetPasswordPage,
  },

  {
    path: "/calendar",
    name: "SchedulerPage",
    component: SchedulerPage,
  },

  {
    path: "/user-group",
    name: "UserGroup",
    component: UserGroupComponent,
  },


  {
      path: "/profile/my",
      name: "MyProfile",
      component: MyProfile,
  },

    
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  const isUserLoggedIn = localStorage.getItem('isUserLoggedIn');

  // List of routes that are accessible only when not logged in
  const publicOnlyRoutes = ['/login', '/signup', '/verify-email', '/reset-password'];

  if (isUserLoggedIn === 'true' && publicOnlyRoutes.includes(to.path)) {
    // If the user is logged in and trying to access a public-only route,
    // redirect them to the dashboard
    next('/dashboard');
  }
  else {
    // Otherwise, allow them to proceed to the requested route
    next();
  }
});


export default router;
