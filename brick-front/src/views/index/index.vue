<script setup>
import LanguageChange from "@/components/LanguageChange.vue";
import {Odometer} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";

const router = useRouter();

const handleHeaderSelect = (key, keyPath) => {
  switch (key) {
    case "1": {
      router.push('/')
      break
    }
    case "2": {
      router.push('/news')
      break
    }
    case "4": {
      router.push('/console')
      break
    }
  }
}
</script>

<template>
  <el-container class="main">

    <el-header class="brick-el-header">
      <el-menu
        :default-active="`1`"
        mode="horizontal"
        :ellipsis="false"
        @select="handleHeaderSelect"
        background-color="rgba(0, 0, 0, 0.1)"
        text-color="#E5EAF3"
        active-text-color="#E5EAF3"
        class="brick-el-header-menu">
        <el-menu-item index="1" class="brick-el-header-menu-item">
          {{ $t('serverName') }}
        </el-menu-item>
        <el-menu-item index="2" class="brick-el-header-menu-item">
          {{ $t('index.news') }}
        </el-menu-item>
        <div style="flex-grow: 1"></div>
        <el-menu-item index="3" class="brick-el-header-menu-item">
          <LanguageChange/>
        </el-menu-item>
        <el-menu-item index="4" class="brick-el-header-menu-item">
          <el-tooltip
            effect="light"
            :content="$t('index.consoleTooltip')"
            placement="bottom-end">
            <el-icon>
              <Odometer/>
            </el-icon>
          </el-tooltip>
        </el-menu-item>
      </el-menu>
    </el-header>

    <el-main class="brick-el-main">
      <router-view></router-view>
    </el-main>

  </el-container>
</template>

<style scoped lang="less">
.main {
  height: 100vh;
}

.brick-el-header {
  padding: 0;
  height: 64px;

  .brick-el-header-menu {
    display: flex;
    justify-content: flex-end;
    height: 64px;
    border: 0;

    .brick-el-header-menu-item {
      border-radius: 20px;
      border-bottom: none;
      transition: all 0.8s;
      background-color: transparent;

      &:first-child {
        width: 200px;
        font-family: Lot, sans-serif !important;
        font-size: 32px;

        &:hover {
          color: #E5EAF3;
        }
      }

      &:hover {
        color: #66b1ff;
      }
    }
  }
}

.brick-el-main {
  flex-grow: 1;
  padding: 0;
}
</style>
