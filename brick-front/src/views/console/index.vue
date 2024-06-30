<script setup>
import {Document, Setting, Menu as IconMenu} from "@element-plus/icons-vue";
import router from "@/router/index.js";

const handleHeaderSelect = (key, keyPath) => {
  if (key === '1') {
    router.push('/')
  }
}

const handleAsideSelect = (key, keyPath) => {
  console.log(key, keyPath)
}
</script>

<template>
  <div>
    <el-container>

      <el-header class="brick-el-header">
        <el-menu
          :default-active="'1'"
          mode="horizontal"
          :ellipsis="false"
          @select="handleHeaderSelect"
          background-color="rgba(0, 0, 0, 0.05)"
          text-color="#000000"
          active-text-color="#000000"
          class="brick-el-header-menu">
          <el-menu-item index="1" class="brick-el-header-menu-item">
            {{ $t('serverName') }}
          </el-menu-item>
          <div style="flex-grow: 1"></div>
          <el-menu-item index="2" class="brick-el-header-menu-item">
            Processing Center
          </el-menu-item>
          <el-menu-item index="3" class="brick-el-header-menu-item">
            User Console
          </el-menu-item>
        </el-menu>
      </el-header>

      <el-container>
        <el-aside class="brick-el-aside" width="200px">
          <el-menu
            default-active="1"
            @select="handleAsideSelect"
            class="brick-el-aside-menu">
            <el-menu-item-group title="Group One">
              <el-menu-item index="1" class="brick-el-aside-menu-item">
                <el-icon>
                  <icon-menu/>
                </el-icon>
                <span>Navigator Two</span>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group Two">
              <el-menu-item index="2" class="brick-el-aside-menu-item">
                <el-icon>
                  <document/>
                </el-icon>
                <span>Navigator Three</span>
              </el-menu-item>
              <el-menu-item index="3" class="brick-el-aside-menu-item">
                <el-icon>
                  <setting/>
                </el-icon>
                <span>Navigator Four</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-menu>
        </el-aside>
        <el-main class="brick-el-main">
          <router-view></router-view>
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<style scoped lang="less">
.brick-el-header {
  padding: 0;
  height: 50px;

  .brick-el-header-menu {
    display: flex;
    justify-content: flex-end;
    height: 50px;

    .brick-el-header-menu-item {
      border-radius: 20px;
      border-bottom: none;
      transition: all 0.8s;
      height: 50px;
      background-color: transparent;

      &:first-child {
        width: 200px;
        font-family: Lot,sans-serif !important;
        font-size: 26px;

        &:hover {
          color: #FFFFFF;
        }
      }

      &:hover {
        color: #66b1ff;
      }
    }
  }
}

.brick-el-aside {
  .brick-el-aside-menu {
    display: flex;
    flex-direction: column;
    height: 100vh;

    .brick-el-aside-menu-item {
      border-radius: 20px;
      transition: all 0.8s;
      height: 50px;
    }
  }

  &::-webkit-scrollbar {
    display: none;
  }
}

.brick-el-main {
  padding: 0;
}
</style>
