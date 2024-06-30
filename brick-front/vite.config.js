import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  publicPath: './',
  plugins: [
    vue()
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  css: {
    preprocessorOptions: {
      less: {
        javascriptEnabled: true
      }
    }
  },
  server: {
    proxy: {
      '/bing': {
        target: 'https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/bing/, '')
      },
      '/yi': {
        //https://v1.hitokoto.cn
        //https://international.v1.hitokoto.cn
        target: 'https://international.v1.hitokoto.cn',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/yi/, '')
      }
    }
  }
})
