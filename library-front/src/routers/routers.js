import Layout from '../pages/Layout/index.vue'

export const staticRoutes = [
    {
        // 登录
        path: "/login",
        component: () => import("../pages/Login/index.vue"),
        name: "Login",
    },
    {
        path: "/",
        component: Layout,
        name: "Layout",
        redirect: "/home", //重定向到主页
        children: [
            {
                //主页
                path: "home",
                component: () => import("../pages/Home/index.vue"),
                name: "Home",
            },
            {
                //注册
                path: "register",
                component: () => import("../pages/Register/index.vue"),
                name: "Register",
            },
            {
                //个人中心
                path: "userCenter",
                component: () => import("../pages/userCenter/index.vue"),
                name: "userCenter",
            },
            //===============会员部分===================
            {
                //会员列表
                path: "user",
                component: () => import("../pages/User/index.vue"),
                name: "User",
            },
            {
                //会员添加
                path: "addUser",
                component: () => import("../pages/User/AddUser/index.vue"),
                name: "addUser",
            },
            {
                //会员编辑
                path: "editUser",
                component: () => import("../pages/User/EditUser/index.vue"),
                name: "editUser",
            },
            {
                //空状态
                path: "empty",
                component: () => import("../components/Empty.vue"),
                name: "empty",
            },
            //===============管理员部分===================
            {
                //管理员列表
                path: "admin",
                component: () => import("../pages/Admin/index.vue"),
                name: "Admin",
            },
            {
                //管理员添加
                path: "addAdmin",
                component: () => import("../pages/Admin/AddAdmin/index.vue"),
                name: "addAdmin",
            },
            {
                //管理员编辑
                path: "editAdmin",
                component: () => import("../pages/Admin/EditAdmin/index.vue"),
                name: "editAdmin",
            },
            //===============分类部分===================
            {
                //分类列表
                path: "category",
                component: () => import("../pages/Category/index.vue"),
                name: "Category",
            },
            {
                //分类添加
                path: "addCategory",
                component: () => import("../pages/Category/AddCategory/index.vue"),
                name: "addCategory",
            },
            {
                //分类编辑
                path: "editCategory",
                component: () => import("../pages/Category/EditCategory/index.vue"),
                name: "editCategory",
            },
            {
                //分类展示
                path: "showCategory",
                component: () => import("../pages/Category/ShowCategory/index.vue"),
                name: "showCategory",
            },
            //===============图书部分===================
            {
                //图书列表
                path: "book",
                component: () => import("../pages/Book/index.vue"),
                name: "Book",
            },
            {
                //图书添加
                path: "addBook",
                component: () => import("../pages/Book/AddBook/index.vue"),
                name: "addBook",
            },
            {
                //图书编辑
                path: "editBook",
                component: () => import("../pages/Book/EditBook/index.vue"),
                name: "editBook",
            },
            {
                //图书详情
                path: "showBook",
                component: () => import("../pages/Book/ShowBook/index.vue"),
                name: "showBook",
            },
            //===============借书部分===================
            {
                //借书列表
                path: "borrow",
                component: () => import("../pages/Borrow/index.vue"),
                name: "Borrow",
            },
            {
                //借书添加
                path: "addBorrow",
                component: () => import("../pages/Borrow/AddBorrow/index.vue"),
                name: "addBorrow",
            },
            {
                //借书编辑
                path: "editBorrow",
                component: () => import("../pages/Borrow/EditBorrow/index.vue"),
                name: "editBorrow",
            },
            //===============还书部分===================
            {
                //还书列表
                path: "retur",
                component: () => import("../pages/Retur/index.vue"),
                name: "Retur",
            },
        ],
    },
    {
        // 找不到
        path: "/:pathMatch(.*)*",
        component: () => import("../pages/NotFound/index.vue"),
        name: "NotFound",
    },
  ];