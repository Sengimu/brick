import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "@/stores/index.js";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue')
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('@/views/signup/index.vue')
    },
    {
      path: '/',
      name: 'index',
      component: () => import('@/views/index/index.vue'),
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('@/views/index/home/index.vue')
        },
        {
          path: '/news',
          name: 'news',
          component: () => import('@/views/index/news/index.vue')
        }
      ]
    },
    {
      path: '/console',
      name: 'console',
      component: () => import('@/views/console/index.vue'),
      redirect: '/console/user',
      children: [
        {
          path: '/console/user',
          name: 'consoleUser',
          component: () => import('@/views/console/user/index.vue')
        }
      ]
    },
    {
      path: "/404",
      name: '404',
      component: () => import("@/views/error/404/index.vue")
    },
    {
      path: "/:catchAll(.*)",
      redirect: '/404',
    }
  ]
})

router.beforeEach((to, from) => {

  const userStore = useUserStore()
  if (to.path.startsWith('/console') && userStore.token === '') {
    console.log(userStore.token)
    return {name: 'login'}
  }

  if (to.path.startsWith('/login') && userStore.token !== '') {
    console.log(userStore.token)
    return {name: 'console'};
  }
})

export default router
