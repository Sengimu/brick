<script setup>
import {userLoginRequest} from "@/api/user.js";
import {ref} from "vue";
import {useUserStore} from "@/stores/index.js";
import {ElMessage} from 'element-plus'
import LanguageChange from "@/components/LanguageChange.vue";

const loginForm = ref({
  email: '',
  password: ''
})

const userStore = useUserStore();
const login = async () => {
  const {code, msg, data} = await userLoginRequest(loginForm.value.email, loginForm.value.password);
  if (code === 1) {
    userStore.setToken(data)
    ElMessage.success(data)
  } else {
    ElMessage.error(msg)
  }
}
</script>

<template>
  <div class="login">
    <div>
      <el-card class="card">
        <h2>{{ $t('serverName') }}</h2>
        <el-form :model="loginForm">
          <el-form-item>
            <el-input v-model="loginForm.email" prefix-icon="Message" :placeholder="$t('login.email')"
                      class="input font-size"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model="loginForm.password" prefix-icon="Hide" :placeholder="$t('login.password')"
                      type="password"
                      class="input font-size"/>
          </el-form-item>
          <el-form-item class="button">
            <el-button type="primary" @click="login" round class="input font-size">{{ $t('login.button') }}</el-button>
          </el-form-item>
          <el-form-item>
            <el-link type="primary" :icon="'Promotion'" :underline="false"
                     class="link font-size">{{ $t('login.forget') }}
            </el-link>
          </el-form-item>
        </el-form>
        <div class="language">
          <LanguageChange/>
        </div>
      </el-card>
    </div>
    <div class="signup">
      <el-text class="text font-size">{{ $t('login.noAccount') }}&nbsp;</el-text>
      <el-link type="info" @click="$router.push('/signup')" class="link font-size">{{ $t('login.toSignUp') }}</el-link>
    </div>
  </div>
</template>

<style scoped lang="less">
@media (width <= 1440px) {
  .login {
    .card {
      width: 35vw !important;

      .input {
        width: 20vw !important;
      }
    }
  }
}

@media (width <= 992px) {
  .login {
    .card {
      width: 45vw !important;

      .input {
        width: 30vw !important;
      }
    }
  }
}

@media (width <= 576px) {
  .login {
    .card {
      width: 80vw !important;

      h2 {
        font-size: 18px !important;
      }

      .input {
        width: 55vw !important;
      }
    }
  }

  .font-size {
    font-size: 12px !important;
  }
}

.login {
  width: 100vw;
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
    width: 25vw;
    border-radius: 10px;

    h2 {
      margin-bottom: 30px;
      text-align: center;
      font-size: 28px;
    }

    .input {
      width: 15vw;
    }

    .button {
      margin-bottom: 5px;
    }

    .link {
      margin-bottom: 20px;
    }

    .language {
      position: absolute;
      top: 10px;
      right: 10px;
    }
  }

  .signup {
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
