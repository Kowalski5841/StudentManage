<template>
  <div class="container">
    <el-card>
      <div slot="header">
        <h2>学生列表</h2>
      </div>
      <el-form :inline="true">
        <el-form-item label="学生姓名：">
          <el-input v-model="param.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="query">查询</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table
          :data="list"
          stripe
          style="width: 100%">
        <el-table-column
            prop="name"
            label="学生姓名">
        </el-table-column>
        <el-table-column
            prop="area"
            label="赛事成绩">
        </el-table-column>
        <el-table-column
            prop="address"
            label="家庭地址">
        </el-table-column>
<!--        <el-table-column-->
<!--            prop="starLevel"-->
<!--            label="星级">-->
<!--          <template slot-scope="scope">-->
<!--            <el-rate :value="scope.row.starLevel" disabled></el-rate>-->
<!--          </template>-->
<!--        </el-table-column>-->
        <el-table-column
            prop="tag"
            label="任职">
          <template slot-scope="scope">
            <el-tag type="success" style="margin: 5px" v-for="tag in scope.row.tag.split(',')" :key="tag">{{ tag }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="showInfo(scope.row)">查看</el-button>
            <el-button type="text" size="small" @click="showEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="margin-top: 20px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="param.pageNum"
          :page-sizes="[3, 5, 10, 15]"
          :page-size="param.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

    </el-card>
    <el-dialog
        title="修改信息"
        :visible.sync="dialogVisible"
        width="55%">
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
            <el-cascader v-if="dialogVisible" :props="props" v-model="dist" style="width: 300px;"></el-cascader>
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
            :file-list="fileList"
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


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateForm">编 辑</el-button>
  </span>
    </el-dialog>



    <el-dialog
        title="查看信息"
        :visible.sync="dialogShow"
        width="55%">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="form">
        <el-form-item label="学生姓名:" prop="name">
          <el-label v-model="form.name">{{form.name}}</el-label>
        </el-form-item>
        <el-form-item label="赛事成绩:" prop="area">
          <el-label v-model="form.area">{{form.area}}</el-label>
        </el-form-item>
        <el-form-item label="家庭地址:" prop="addr">
          <div style="display: flex">
            <!--  级联选择器          -->
            <el-label v-if="dialogShow" :props="props" v-model="dist" >{{dist.toString(),"[]"}},{{form.addr}}</el-label>
          </div>
        </el-form-item>
        <!--        <el-form-item label="星级" prop="starLevel">-->
        <!--          <el-rate v-model="form.starLevel" style="margin-top: 10px"></el-rate>-->
        <!--        </el-form-item>-->
        <el-form-item label="绩点" prop="score">
          <el-lable v-model="form.score" :precision="1" :step="1" :max="5" :min="1">{{form.score}}</el-lable>
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
          <el-label
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
          >
          </el-label>
        </el-form-item>
        <el-form-item label="肖像:" prop="pic">

          <!--
                 action 上传后台接口地址
                 list-type 文件列表的类型
                 on-success 上传成功触发的函数
                 on-remove 删除图片时触发的函数

                 --><el-upload
            :file-list="fileList"
            :action=" path + '/file/upload'"
            list-type="picture-card"
            ref="pic"
            :on-success="picSuccess"
            :on-remove="picRemove"
            :on-exceed="picExceed"
            :limit="1">

<!--          <i class="el-icon-plus"></i>-->
        </el-upload>
        </el-form-item>

        <el-form-item label="教师评价:" prop="introduce">
          <el-label v-model="form.introduce" type="textarea">{{form.introduce}}</el-label>
        </el-form-item>
        <el-form-item label="自我评价:" prop="rooms">
          <el-label v-model="form.rooms" type="textarea">{{form.rooms}}</el-label>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogShow = false">确 认</el-button>
<!--    <el-button type="primary" @click="updateForm">编 辑</el-button>-->
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
        name:'',
        uid:0
      },
      total:0,
      list:[],
      dialogVisible:false,
      dialogShow:false,
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
        rooms:'',
        uid:'',
        addr:'',
        tagArr:[]
      },
      rules:{
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
        rooms:[
          {required:true,message:'请输入学生评价',trigger:'blur'}
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
  methods:{
    updateForm(){
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
          this.$http.put('/homestay',this.form).then(res=>{
            if(res.data.code===200){
              this.$message.success('编辑成功！')
              this.queryList()
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
    },
    showInfo(row){
      this.form = {...row}
      //['中国'，'山东省'，'济南市'，'历下区'，'趵突泉XXX']
      let addrs = this.form.address.split('，')
      this.$set(this.form,'addr',addrs[addrs.length-1])
      this.dist = addrs.filter((x,i)=>{
        return i>0&&i<4
      })
      this.$set(this.form,'tagArr',this.form.tag.split(','))

      this.fileList=[{url:this.form.pic,name:''}]

      this.dialogShow = true
    },
    showEdit(row){
      this.form = {...row}
      //['中国'，'山东省'，'济南市'，'历下区'，'趵突泉XXX']
      let addrs = this.form.address.split('，')
      this.$set(this.form,'addr',addrs[addrs.length-1])
      this.dist = addrs.filter((x,i)=>{
        return i>0&&i<4
      })
      this.$set(this.form,'tagArr',this.form.tag.split(','))

      this.fileList=[{url:this.form.pic,name:''}]

      this.dialogVisible = true
    },
    del(id){
      this.$confirm('此操作将永久删除该学生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete('/homestay?id='+ id).then(res=>{
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
    query(){
      this.param.pageNum = 1
      this.queryList()
    },
    resetForm() {
      this.param.name = ''
      this.param.pageNum = 1
      this.queryList()
    },
    handleSizeChange(val) {
     this.param.pageSize = val
      this.queryList()
    },
    handleCurrentChange(val) {
      this.param.pageNum = val
      this.queryList()
    },
    queryList(){
      this.$http.get('/homestay',{params : this.param}).then(res=>{
        this.list = res.data.data.rows
        this.total = res.data.data.total
      })
    }
  },
  created() {
        this.param.uid = JSON.parse(localStorage.getItem('userInfo')).id
        this.queryList()
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