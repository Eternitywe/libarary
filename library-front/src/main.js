import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./routers/index";
import './style.css'
import App from './App.vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'



const app = createApp(App)

app.use(ElementPlus)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router).mount('#app')
