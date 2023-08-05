<template>
  <div class="container">
    <el-card>
      <div slot="header">
        <h2>添加学生</h2>
      </div>
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="form">
        <el-form-item label="学生姓名:" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="赛事成绩:" prop="area">
          <el-input v-model="form.area"></el-input>
          </el-form-item>
        <el-form-item label="家庭地址:" prop="addr">
          <div style="display: flex">
<!--  级联选择器          -->
            <el-cascader :props="props" v-model="dist" style="width: 300px;"></el-cascader>
            <el-input v-model="form.addr"></el-input>
          </div>
        </el-form-item>
<!--        <el-form-item label="星级" prop="starLevel">-->
<!--          <el-rate v-model="form.starLevel" style="margin-top: 10px"></el-rate>-->
<!--        </el-form-item>-->
        <el-form-item label="绩点" prop="score">
          <el-input-number v-model="form.score" :precision="1" :step="1" :max="5" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="任职" prop="tagArr">
          <el-tag
              :key="tag"
              v-for="tag in form.tagArr"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
            {{tag}}
          </el-tag>
          <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
          >
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
        </el-form-item>
        <el-form-item label="肖像:" prop="pic">

<!--
       action 上传后台接口地址
       list-type 文件列表的类型
       on-success 上传成功触发的函数
       on-remove 删除图片时触发的函数

       --><el-upload
              :action=" path + '/file/upload'"
              list-type="picture-card"
              ref="pic"
              :on-success="picSuccess"
              :on-remove="picRemove"
              :on-exceed="picExceed"
              :limit="1">

            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="教师评价:" prop="introduce">
          <el-input v-model="form.introduce" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="学生学号:" prop="studentNumber">
          <el-input v-model="form.studentNumber"></el-input>
        </el-form-item>
        <el-form-item label="学生密码:" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="addForm">添加</el-button>
          <el-button @click="resetForm">重置</el-button>
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
    return{
      inputVisible: false,
      inputValue: '',
      props:{
        lazy:true,
        lazyLoad(node,resolve){
          const{ level } = node;
          let name = node.value || ''
          let params = {
            name,
            level
          }
          _this.$http.get('/user/queryDistByName',{params}).then(res=>{
            let nodes = res.data.data.map(item=>{
              return{
                value: item.name,
                label: item.name,
                leaf: level>=2
              }
            })
            resolve(nodes)
          })
        }
      },
      dist:[],
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
        tagArr:[],
        studentNumber:'',
        password:'',
      },
      rules:{
        studentNumber:[
          {required:true,message:'请输入学生学号',trigger:'blur'}
        ],
        password:[
          {required:true,message:'请输入学生密码',trigger:'blur'}
        ],
        name:[
          {required:true,message:'请输入学生姓名',trigger:'blur'}
        ],
        area:[
          {required:true,message:'请输入赛事成绩',trigger:'blur'}
        ],
        // starLevel:[
        //   { type:'number',max:5,min:1, required:true,message:'星级至少一星！',trigger:'blur'}
        // ],
        score:[
          {required:true,message:'请输入绩点',trigger:'blur'}
        ],
        pic:[
          {required:true,message:'请上传肖像',trigger:'blur'}
        ],
        introduce:[
          {required:true,message:'请输入教师评价',trigger:'blur'}
        ],
        addr:[
          {required:true,message:'请输入家庭地址',trigger:'blur'}
        ],
        tagArr:[
          {required:true,message:'请添加任职',trigger:'blur'}
        ],
      }
    }
  },
  methods: {
    addForm(){
      this.$refs.form.validate( valid =>{
        if(valid){
          if(this.dist.length>0){
            //中国，山东省，济南市，历下区
            this.form.address = '中国，' + this.dist.join('，') + '，' + this.form.addr
          }else{
            this.$message.error('请选择省市区!')
            return
          }
          if(this.form.tagArr.length>0){
            this.form.tag = this.form.tagArr.join(',')
          }
          this.form.uid = JSON.parse(localStorage.getItem('userInfo')).id
          console.log(this.form)
          this.$http.post('/homestay',this.form).then(res=>{
            if(res.data.code===200){
              this.$message.success('添加成功！')
              this.resetForm()
            }else{
              this.$message.error('添加失败！')
            }
          })
        }
      })
    },
    resetForm(){
      //重置表单数据
      this.$refs.form.resetFields()
      //清空省市区级联数据
      this.dist = []
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
      this.$message.error('肖像只能上传一张！')

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
        if(this.form.tagArr.indexOf(inputValue)>=0) {
          this.$message.error('该任职已存在！')
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