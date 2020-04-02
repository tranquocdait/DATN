import { RouteInfo } from './sidebar.metadata';

export const ROUTES: RouteInfo[] = [
    {
        path: '',
        title: 'Admin',
        icon: '',
        class: 'nav-small-cap',
        label: '',
        labelClass: '',
        extralink: true,
        submenu: []
    },
    {
        path: '/component/list-user',
        title: 'User',
        icon: 'mdi mdi-account',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
    },
    {
        path: '/component/list-post',
        title: 'Bài đăng',
        icon: 'mdi mdi-equal',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
    },
    {
        path: '/component/list-item',
        title: 'Đơn hàng',
        icon: 'mdi mdi-library-books',
        class: '',
        label: '',
        labelClass: '',
        extralink: false,
        submenu: []
    }
];
