<template>
  <div class="container">
    <el-card>
      <div slot="header">
        <h2>添加老师</h2>
      </div>
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="form">
        <el-form-item label="老师姓名：" prop="nickname">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="老师账号：" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="老师密码：" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="addForm">添加</el-button>
          <el-button type="primary" @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "add",
  data(){
    let _this = this
    return {
      inputVisible: false,
      inputValue: '',
      props:{
        //开启懒加载模式
        lazy:true,
        lazyLoad(node,resolve){
          const{ level } =node;
          let name = node.value || ''
          let params = {
            name:name,
            level:level
          }
          _this.$http.get('/user/queryDistByName',{params}).then(res=>{
            let nodes = res.data.data.map(item=>{
              return {
                value: item.name,
                label: item.name,
                leaf:  level>=2
              }
            })
            resolve(nodes)
          })
        }
      },
      form:{
        name:'',
        area:'',
        address:'',
        starLevel:0,
        score:0,
        tag:'',
        pic:'',
        introduce:'',
        uid:'',
        addr:'',
        tagArr:[]

      }
      ,
      dist:[],

      rules:{
        nickname:[
          {required:true,message:'请输入老师姓名',trigger:'blur'}
        ],
        username:[
          {required:true,message:'请输入老师账号',trigger:'blur'}
        ],
        password:[
          {required:true,message:'请输入老师密码',trigger:'blur'}
        ],

      }
    }

  },
  methods: {
    addForm(){
      //2.发请求
      this.$http.post('/user/addTeacher',this.form).then(res=>{
        if(res.data.code===200){
          this.$message.success('添加成功！')
          //重置表单
          // this.resetForm()
        }else{
          this.$message.error('添加失败！')
        }
      })
      //1.验证表单
      // this.$refs.form.validate( valid=>{
      //   if(valid){
      //     //1.1验证级联下拉框有没有选
      //     if(this.dist.length>0){
      //       //如果选了，就进行拼接
      //       this.form.address = '中国，' + this.dist.join('，') + '，' + this.form.addr
      //     }else{
      //       this.$message.error('请选择省市区')
      //       return
      //     }
      //     if(this.form.tagArr.length>0){
      //       this.form.tag = this.form.tagArr.join(',')
      //     }
      //     //先获取用户id
      //     this.form.uid=JSON.parse(localStorage.getItem('userInfo')).id
      //
      //
      //   }
      // })
    },
    resetForm(){
      //重置表单数据
      this.$refs.form.resetFields()
      //清空省市区级联数据
      this.dist=[]
      //清空图片
      this.$refs.pic.clearFiles()
    },
    picSuccess(res){
      this.form.pic = this.path + res.data
    },
    picRemove(){
      this.form.pic = ''
    },
    picExceed(){
      this.$message.error('封面只能上传一张！')
    },
    handleClose(tag) {
      this.form.tagArr.splice(this.form.tagArr.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if(this.form.tagArr.indexOf(inputValue)>=0){
          this.$message.error('该标签已存在！')
          return
        }
        this.form.tagArr.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    }
  }
}
</script>

<style scoped>
.el-tag + .el-tag {

  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>