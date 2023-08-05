<template>
  <div class="container">
    <el-container>
      <el-header>
        <div>
          <el-button type="text" style="color:white;margin-top:-25px">
            <h2 style="font-size: 30px">学生综合素质管理系统</h2>
          </el-button>
        </div>
        <el-dropdown style="margin-right: 20px">
  <span class="el-dropdown-link">
    <span style="font-size: 14px;color:white">
    尊敬的{{ user.nickname }}，您好<i class="el-icon-arrow-down el-icon--right"></i>
    </span>

  </span>
          <el-dropdown-menu slot="dropdown">

            <!--          这个click.native是给组件使用原生事件-->
            <el-dropdown-item icon="el-icon-switch-button" @click.native="exit">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu router default-active="/classify/addStu" style="height: 100%">
            <el-submenu index="manager">
              <template slot="title">
                <i class="el-icon-s-home"></i>
                <span>用户管理</span>
              </template>
              <el-menu-item index="/classify/addStu">
                <i class="el-icon-plus"></i>
                <span>添加学生</span>
              </el-menu-item>
              <el-menu-item index="/classify/addTeach">
                <i class="el-icon-plus"></i>
                <span>添加老师</span>
              </el-menu-item>
              <el-menu-item index="/classify/list">
                <i class="el-icon-document"></i>
                <span>学生列表</span>
              </el-menu-item>
              <el-menu-item index="/classify/listTeacher">
                <i class="el-icon-document"></i>
                <span>老师列表</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main>
            <!--          路由出口-->
            <router-view></router-view>
          </el-main>
          <el-footer>
            <h3>SDUT</h3>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      user:{}
    }
  },
  methods:{
    exit(){
      //清空信息
      localStorage.removeItem('userInfo')
      //跳回登录
      this.$router.push('/')
    }

  },

  //八大生命周期函数之一    当页面开始加载到结束整个流程的函数
  created() {
    //防止用户未登录就进入后台
    let user = JSON.parse(localStorage.getItem('userInfo') || '{}')
    if(user.id){
      this.user=user
    }else {
      this.$message.error('请先登录')
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.container,.el-container{
  height: 100%;
}
.el-header,.el-footer{
  background-color: bisque;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
}
.el-footer{
  justify-content: center;
  align-items: center;
  color:white;
}
</style>