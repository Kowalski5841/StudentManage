<template>
  <div class="container">
    <el-container>
      <el-header style="color: #5678a8; background-color: bisque">
        <div class="center">
          <div style="font-weight: 1000; font-size: 32px">
            学生综合素质管理系统
          </div>
        </div>
      </el-header>


      <el-main>
        <div class="login-container">
          <el-card >
            <div class="login-title">账号密码登录</div>

            <el-form :model="form" :rules="rules" ref="form" class="form">
              <el-form-item prop="username">
                <el-input v-model="form.username" placeholder="请输入账号"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="form.password" placeholder="请输入密码" show-password></el-input>
              </el-form-item>
              <el-form-item prop="type">
                <el-select style="width: 100%" v-model="form.type">
                  <el-option label="学生" :value="1"></el-option>
                  <el-option label="老师" :value="2"></el-option>
                  <el-option label="管理员" :value="3"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="warning" style="width: 100%;" @click="login">登录</el-button>

                <el-tooltip manual :value="checked===tooltip" effect="dark" content="请先阅读并同意协议" placement="top-start">
                  <el-checkbox v-model="checked" @change="ty"></el-checkbox>
                </el-tooltip>
                <span class="p" >
                  阅读并同意
                  <a href="#">个人信息保护政策</a>
                </span>

              </el-form-item>
            </el-form>
          </el-card>
        </div>
      </el-main>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      tooltip:true,
      checked:false,
      form:{
          username:'',password:'',type:1,
        },
      rules:{
          username: [
              /*required是设置必填项的*/
              /*trigger是触发器，触发方式*/
            {required:true,message:'请填写账号' ,trigger:'blur'}
          ],
        password: [
            {required:true,message:'请填写密码' ,trigger:'blur'}
          ]
      }
    }
  },
  methods:{
    ty(){
      this.tooltip = !this.checked
    },
    login(){
      /*this.$refs是所有的ref对象*/
      /*validate是 element表单组件封装的验证表单的方法*/
      /*()>{}是箭头函数，类似于匿名函数*/
      /*valid是验证结果，形参*/
      this.$refs.form.validate((valid)=>{
        if(valid){
          if(!this.checked){
            //只要进到这里面，那么就是被勾选的状态
              this.tooltip =false
              return
          }

          //发请求调用后台接口去登录--------------------------//
          //this.$message.success('登陆成功')
          this.$http.get('/user/login',{params:this.form}).then(res=>{
            //res 包含了所有信息：响应头 相应数据 状态码 访问地址等等
            let result = res.data
            if(result.code ===200){
              this.$message.success(result.msg)
              //存储用户信息
              localStorage.setItem('userInfo',JSON.stringify(result.data))
              if(result.data.type===1){
                //全局路由对象 this.$router push() 切换路由地址
                this.$router.push('/home')
              }else if(result.data.type===2){
                this.$router.push('/layout')
              }else{
                this.$router.push('/manager')
              }
            }else {
              this.$message.error(result.msg)
            }
          })
          //这个params可以自动拼接参数
        }
        //如果是false，那么方法会自动提示
      })
    }
  }
}
</script>
<!--scoped是一个私有化的作用。这个页面的样式不会影响其他页面的布局，只在当前页面生效-->
<style scoped>
    .container,.el-container{
      height: 100%;
    }
    .el-header{
      /*important关键字：强制设置样式*/
      height: 90px !important;
      /*开启弹性布局，默认从左往右排列 这个配置只作用于最近一层的子元素，如果是子子元素就不生效了。
        这个文档中支队center生效*/
      display: flex;
      /*设置侧轴对其方式*/
      align-items: center;


    }
    .center{
      max-width: 1160px;
      min-width: 1080px;
      /*改变图标以及按钮的排列方向*/
      display: flex;
      /*设置主轴的对齐方式 为左右对齐*/
      justify-content: space-between;
      /*设置元素外边距，0是上下边距，auto是左右自动居中*/
      margin:0 auto;
    }
    .login-container{
      background: url("../../assets/background2.jpg") repeat-y;
      background-size: cover;
      height: 100%;
      /*设置登录表单*/
      display: flex;
      flex-direction: row-reverse;
      ;
    }
    .el-main{
      padding: 0;
    }

    .login-container .el-card{
      width: 340px;
      height: 350px;
      padding: 20px;
      margin-right: 25vw;
      margin-top: 90px;

      /**
      单位：
      %占父元素百分比
      vw,vh占浏览器窗口的百分比
       */
    }

    .login-title{
      font-size: 17px;
      font-weight: 600;
    }
    .form{
      margin-top: 20px;
    }


    /*给组件加样式但是不生效的话，可以加一个deep*/
    /deep/.el-checkbox__inner{
      border-radius: 20px;
    }

    .p{
      margin-left: 4px;
      color:#999;
      line-height: 12px;
      font:12px/1.5 Sans-serif;
    }

    .form a{
      color:#0096f6;
      /*设置下划线*/
      text-decoration: underline;
      border-bottom: none;
    }
</style>