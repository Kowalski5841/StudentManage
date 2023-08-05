<template>
      <div class="container">
        <el-container>
          <el-header>
            <div>
              <el-button type="text" style="color: white;">
                <h2 style="font-size: 30px; ">学生综合素质管理系统-教师端</h2>
              </el-button>
            </div>
            <el-dropdown>
              <span class="el-dropdown-link">
                <span style="font-size: 14px;color: white">
                  尊敬的{{ user.nickname }}老师<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-switch-button" @click.native="exit">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-header>
          <el-container>
            <el-aside width="200px">
              <el-menu
                router
                default-active="/homestay/add" style="height: 100%"
              >
                <el-submenu index="homestay">
                  <template slot="title">
                    <i class="el-icon-s-home"></i>
                    <span>学生管理</span>
                  </template>
                  <el-menu-item index="/homestay/add">
                    <i class="el-icon-plus"></i>
                    <span>添加学生</span>
                  </el-menu-item>
                  <el-menu-item index="/homestay/list">
                    <i class="el-icon-document"></i>
                    <span>学生列表</span>
                  </el-menu-item>
                </el-submenu>
              </el-menu>
            </el-aside>
            <el-container>
              <el-main>
<!--          路由出口      -->
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
     /* "清空用户信息"*/
      localStorage.removeItem('userInfo')
      this.$router.push('/')
    }
  },
  created() {
    let user = JSON.parse(localStorage.getItem('userInfo') || '{}')
    if(user.id){
      this.user =user
    }else{
      this.$message.error("请先去登录")
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
      /*左右对齐*/
      justify-content: space-between;
      align-items: center;
    }
    .el-footer{
      justify-content: center;
      color: white;
    }
</style>