<script setup>
import {useI18n} from "vue-i18n"
import {Reading} from "@element-plus/icons-vue";

const {locale} = useI18n()

const localeNames = ["简体中文", "English"]

const changeLocale = (index) => {
  if (index === 0) {
    locale.value = 'zhCN'
  } else {
    locale.value = 'enUS'
  }
}
</script>

<template>
  <el-dropdown>
    <span class="el-ddl"><el-icon><Reading/></el-icon></span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item v-for="(item,index) in localeNames" :key="index" @click="changeLocale(index)"
                          class="el-ddi">
          <div class="el-dropdown-item">
            {{ item }}
          </div>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<style scoped>
@media (width <= 600px) {
  .el-ddl {
    font-size: 12px;
  }

  .el-dropdown-item {
    font-size: 12px;
  }
}

@media (width > 600px) {
  .el-ddl {
    font-size: 14px;
  }

  .el-dropdown-item {
    font-size: 14px;
  }
}

.el-ddl {
  cursor: pointer;
  color: #D1D1D1;
}

:focus-visible {
  outline: -webkit-focus-ring-color auto 0;
}

.el-ddi {
  color: #616161;
}
</style>
