<template>
    <Button v-if="isAuthorized" :type="type" :shape="shape" :size="size" :loading="loading" :disabled="disabled" :htmlType="htmlType" :icon="icon" :long="long" @click="handleClick">
    <slot></slot>
    </Button>        
</template>
<script>
//权限按钮组件 by wangkw
    import Button from 'iview/src/components/button';
    import authUtil from './authUtil'

    export default {
        name: 'AuthButton',
        mixins: [Button],
        components: { Button },
        props: {
            requestConfig:Object//ajax config对象
        },
        computed: {
            isAuthorized(){                
                // if(this.requestConfig==null){
                    // console.log("请求为空!不涉及权限校验")
                    return true;
                // }else{
                //     // console.log("开始校验权限")
                //     return authUtil.checkUrl(this.requestConfig, this.$store.getters.getAuths);
                // }
            }
        }
    };
</script>
