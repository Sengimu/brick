<script setup>
import {userLoginRequest} from "@/api/user.js";
import {onMounted, ref} from "vue";
import {useUserStore} from "@/stores/index.js";
import {ElMessage} from 'element-plus'
import LanguageChange from "@/components/LanguageChange.vue";

const userStore = useUserStore();

onMounted(() => {
  if (userStore.token != null) {
  }
})

const signupForm = ref({
  email: '',
  username: '',
  password: '',
  confirmPassword: ''
})

const signup = async () => {
  ElMessage.info('注册')
}
</script>

<template>
  <div class="signup">
    <el-card class="card">
      <h2>{{ $t('serverName') }}</h2>
      <el-form :model="signupForm">
        <el-form-item>
          <el-input v-model="signupForm.email" prefix-icon="Message" :placeholder="$t('signup.email')"
                    class="input"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="signupForm.username" prefix-icon="Avatar" :placeholder="$t('signup.username')"
                    class="input"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="signupForm.password" prefix-icon="Hide" :placeholder="$t('signup.password')"
                    type="password"
                    class="input"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="signupForm.confirmPassword" prefix-icon="Lock"
                    :placeholder="$t('signup.confirmPassword')"
                    type="password"
                    class="input"/>
        </el-form-item>
        <el-form-item class="button-item">
          <el-button type="primary" @click="signup" round class="input">{{ $t('signup.button') }}</el-button>
        </el-form-item>
      </el-form>
      <div class="language">
        <LanguageChange/>
      </div>
    </el-card>
    <div class="login">
      <el-text class="text font-size">{{ $t('signup.hasAccount') }}&nbsp;</el-text>
      <el-link type="info" @click="$router.push('/login')" class="link font-size">{{ $t('signup.toLogin') }}</el-link>
    </div>
  </div>
</template>

<style scoped lang="less">
.signup {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .card {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 480px;
    border-radius: 10px;

    h2 {
      margin-bottom: 30px;
      text-align: center;
    }

    .input {
      width: 280px;
    }

    .button-item {
      margin-bottom: 20px;
    }

    .language {
      position: absolute;
      top: 2vh;
      right: 0.5vw;
    }
  }

  .login {
    display: flex;
    justify-content: center;
    margin-top: 5px;

    .text {
      font-weight: normal;
      color: #ccc;
    }

    .link {
      font-weight: normal;
      color: #909399;
    }
  }
}

.font-size {
  font-size: 14px;
}
</style>
