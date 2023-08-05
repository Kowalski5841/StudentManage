import Vue from 'vue'
import App from './App.vue'
import router from './router'
//yinru element
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//引入axios
import axios from "axios";
//设置后台接口的前置路径
axios.defaults.baseURL ='http://localhost:9000'
//设置全局变量
Vue.prototype.$http = axios

Vue.prototype.path = 'http://localhost:9000'


//全局注册 element
Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
