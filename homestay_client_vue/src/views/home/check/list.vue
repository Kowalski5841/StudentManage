<template>
  <div class="container">
    <el-card>
      <div slot="header">
        <h2>我的信息</h2>
      </div>
      <!--      先去定义后台接口-->
      <!--homestayServiceImpl接口-->
      <el-form>
        <el-table
            :data="list"
            stripe
            style="width: 100%">
          <el-table-column
              prop="name"
              label="用户名"
          >
          </el-table-column>
<!--          需要把这里的score改掉，改成成绩-->
          <el-table-column
              prop="score"
              label="成绩">
          </el-table-column>
          <el-table-column
              prop="introduce"
              label="老师评价">
          </el-table-column>
          <el-table-column
              prop="area"
              label="赛事成绩">
          </el-table-column>
          <el-table-column
              prop="rooms"
              label="自我评价">
          </el-table-column>

          <el-table-column
              label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showEdit(scope.row)">修改自我评价</el-button>
            </template>
          </el-table-column>

        </el-table>

      </el-form>

    </el-card>

    <el-dialog
        title="修改自我评价"
        :visible.sync="dialogVisible"
        width="60%">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="form">
        <el-form-item label="自我评价" prop="password">
          <el-input v-model="form.rooms" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false" >取 消</el-button>
    <el-button type="primary" @click="updateForm">编 辑</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "list",
  data(){
    let _this = this
    return{
      distFlag:false,
      fileList:[],
      param:{
        pageNum: 1,
        pageSize: 5,
        name: '',
        uid: 0
      },
      total: 0,
      list: [],
      dialogVisible:false,

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

      rules:{name:[
          {required:true,message:'请输入民宿名称',trigger:'blur'}
        ],
        area:[
          {required:true,message:'请输入核心区域',trigger:'blur'}
        ],
        starLevel:[
          { type:'number',max:5,min:1, required:true,message:'星级至少一星！',trigger:'blur'}
        ],
        score:[
          {required:true,message:'请输入评分',trigger:'blur'}
        ],
        pic:[
          {required:true,message:'请上传封面',trigger:'blur'}
        ],
        introduce:[
          {required:true,message:'请输入民宿介绍',trigger:'blur'}
        ],
        addr:[
          {required:true,message:'请输入民宿地址',trigger:'blur'}
        ],
        tagArr:[
          {required:true,message:'请添加标签',trigger:'blur'}
        ],
      }
    }
  },
  methods:{
    queryByStudentName(){
      let user = localStorage.getItem('userInfo');
      let { username } = JSON.parse(user)
      this.$http.get('/homestay/my?username=' + username,{params : this.params}).then(res=>{
        this.list = [res.data.data]
        this.total = 1
      })
    },

    del(id){
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/homestay?id='+id).then(res=>{
          if(res.data.code===200){
            this.$message.success(res.data.msg)
            this.queryList()
          }else{
            this.$message.error(res.data.msg)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    queryList(){
      this.$http.get('/homestay',{params : this.param}).then(res=>{
        this.list = res.data.data.rows
        this.total = res.data.data.total
      })
    },
    handleSizeChange(val) {
      this.param.pageSize = val
      this.queryList()
    },
    handleCurrentChange(val) {
      this.param.pageNum = val
      this.queryList()

    },
    showEdit(row){
      this.form = {...row}
      //['中国','山东省','济南市','历下区','*********']
      let addrs = this.form.address.split('，')
      this.$set(this.form,'addr',addrs[addrs.length-1])
      this.dist = addrs.filter((x,i)=>{
        return i>0 &&i<4
      })

      this.$set(this.form,'tagArr',this.form.tag.split(','))

      this.fileList = [{url:this.form.pic,name:''}]

      this.dialogVisible = true

      this.queryByStudentName()
    },
    updateForm(){
      //1.验证表单
      this.$refs.form.validate( valid=>{
        if(valid){
          //1.1验证级联下拉框有没有选
          if(this.dist.length>0){
            //如果选了，就进行拼接
            this.form.address = '中国，' + this.dist.join('，') + '，' + this.form.addr
          }else{
            this.$message.error('请选择省市区')
            return
          }
          if(this.form.tagArr.length>0){
            this.form.tag = this.form.tagArr.join(',')
          }
          //先获取用户id
          this.form.uid=JSON.parse(localStorage.getItem('userInfo')).id

          //2.发请求
          this.$http.put('/homestay',this.form).then(res=>{
            if(res.data.code===200){
              this.$message.success('编辑成功！')
              //重置表单
              this.queryByStudentName()
              this.dialogVisible = false
            }else{
              this.$message.error('编辑失败！')
            }
          })
        }
      })
    },
    resetUpdateForm(){
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
    },
  },

  created() {
    //初始化列表 ，让打开页面时就进行一次列表展示
    //先获取用户id。因为这个查询是通过用户id获取的
    this.param.uid = JSON.parse(localStorage.getItem('userInfo')).id
    this.queryByStudentName()
  },

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