<template>
    <div style="width: 100% !important;padding: 100px 0;display: flex;justify-content: center;align-items: center;">
        <div class="error">
            <!-- 图片可保存到本地 -->
            <img src="/resources/404.png"/>
            <h5 style="margin-top: 20px;margin-bottom: 10px">OH 访问异常 :( </h5>
            <span class="back" @click="$router.back()">返回上页</span>
        </div>
    </div>
</template>

<script>
export default {}
</script>

<style scoped lang="less">
.error {
    overflow: hidden;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.error img {
    display: block;
    margin: 0 auto;
    width: 50%;
}

.error p {
    text-align: center;
    font-size: 16px !important;
    font-weight: 600;
}

.back {
    display: block;
    width: 100px;
    text-align: center;
    margin: 20px auto;
    padding: 8px 0;
    border: 1px solid #333;
    border-radius: 100px;
    cursor: pointer;
    color: rgb(27, 27, 27);
    font-size: 15px;
}
</style>
