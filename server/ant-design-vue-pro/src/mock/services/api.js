import Mock from 'mockjs2'
import { builder } from '../util'

const URL = {
  login: '/api/login/account',
  info: '/api/me/info',
  listWebapp: '/api/admin/entity.list',
  saveRole: '/api/admin/save/Role',
  savePermission: '/api/admin/save/Permission',
  saveWebapp: '/api/admin/save/Webapp',
  saveAccount: '/api/admin/save/Account'
}

const token = () => {
  debugger
  return builder({ token: '50068f17c229402b816ac2445636a08f', expSeconds: 86400, expired: false }, '登录成功')
}
Mock.mock(new RegExp(URL.login), 'post', token)

const info = () => {
  return builder({ name: '超级管理员', id: 1, email: null, account: 'admin', disable: false, createTime: '2020-12-01 08:05:48', phone: '1352' }, '')
}
Mock.mock(new RegExp(URL.info), 'get', info)

const listWebapp = (q) => {
  const r = JSON.parse(q.body)
  if (r.entity === 'Account') {
    const v = '{"content":[{"name":"超级管理员","id":1,"email":null,"account":"admin","disable":false,"createTime":"2020-12-01 08:05:48","phone":"1352"},{"name":"admin1","id":2,"email":"231@sdfc.com","account":"admin113132","disable":true,"createTime":"2020-12-01 08:05:48","phone":"13524206659"},{"name":"admin2","id":3,"email":null,"account":"admin2","disable":false,"createTime":"2020-12-01 08:05:48","phone":null},{"name":"李长书","id":4,"email":null,"account":"changshu.li","disable":false,"createTime":"2020-12-08 15:01:59","phone":null}],"pageNumber":0,"pageSize":10,"totalElements":4,"last":true,"totalPages":1,"first":true}'
    return builder(JSON.parse(v), '')
  } else if (r.entity === 'Webapp') {
    const v = '{"content":[{"role":null,"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},{"role":null,"name":"另个应用","id":2,"description":null,"disable":false,"appId":"test1","createTime":"2020-11-26 03:21:43"},{"role":null,"name":"nameest","id":3,"description":"asefdsadf","disable":false,"appId":"tstss","createTime":"2020-12-07 15:40:48"}],"pageNumber":0,"pageSize":10,"totalElements":3,"totalPages":1,"first":true,"last":true}'
    return builder(JSON.parse(v), '')
  } else if (r.entity === 'Role') {
    const v = '{"content":[{"webapp":{"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},"name":"user","id":1,"description":"用户","createTime":"2020-12-03 07:49:57"},{"webapp":{"name":"另个应用","id":2,"description":null,"disable":false,"appId":"test1","createTime":"2020-11-26 03:21:43"},"name":"中国","id":2,"description":"哈哈","createTime":"2020-12-09 12:20:10"}],"pageNumber":0,"pageSize":10,"totalElements":2,"totalPages":1,"first":true,"last":true}'
    return builder(JSON.parse(v), '')
  } else if (r.entity === 'Permission') {
    const v = '{"content":[{"parent":null,"webapp":{"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},"name":"编二层","id":1,"type":"URL","sort":0,"description":null,"createTime":"2020-12-04 07:51:38","uri":"/edit/item"},{"parent":{"name":"哈","id":2,"type":"MENU","sort":0,"description":null,"createTime":"2020-12-10 16:15:17","uri":"/api/i/*"},"webapp":{"name":"另个应用","id":2,"description":null,"disable":false,"appId":"test1","createTime":"2020-11-26 03:21:43"},"name":"哈","id":2,"type":"MENU","sort":0,"description":null,"createTime":"2020-12-10 16:15:17","uri":"/api/i/*"},{"parent":{"name":"哈","id":2,"type":"MENU","sort":0,"description":null,"createTime":"2020-12-10 16:15:17","uri":"/api/i/*"},"webapp":{"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},"name":"层","id":3,"type":"URL","sort":0,"description":null,"createTime":"2020-12-14 15:07:59","uri":"/edit/item"},{"parent":null,"webapp":{"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},"name":"新的建","id":4,"type":"URL","sort":0,"description":null,"createTime":"2020-12-14 15:08:06","uri":"/edit/item"}],"pageNumber":0,"pageSize":10,"totalElements":4,"last":true,"totalPages":1,"first":true}'
    return builder(JSON.parse(v), '')
  }
  throw new Error('Not support REQUEST' + q)
}
Mock.mock(new RegExp(URL.listWebapp), 'post', listWebapp)

const saveAccount = () => {
  const v = JSON.parse('{"name":"马云","id":4,"account":"jack.ma","disable":false,"email":null,"createTime":"2020-12-08 15:01:59","phone":null}')
  return builder(v, '')
}
Mock.mock(new RegExp(URL.saveAccount), 'post', saveAccount)

const saveRole = () => {
  const v = JSON.parse('{"webapp":{"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},"name":"user","id":1,"description":"用户","createTime":"2020-12-03 07:49:57"}')
  return builder(v, '')
}
Mock.mock(new RegExp(URL.saveRole), 'post', saveRole)

const savePermission = () => {
  const v = JSON.parse('{"parent":null,"webapp":{"name":"一个应用","id":1,"description":"第一个菜单","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"},"name":"菜单","id":1,"type":"URL","description":null,"sort":0,"createTime":"2020-12-04 07:51:38","uri":"/edit/item"}')
  return builder(v, '')
}
Mock.mock(new RegExp(URL.savePermission), 'post', savePermission)

const saveWebapp = () => {
  const v = JSON.parse('{"role":null,"name":"一个应用","id":1,"description":"第一个编辑","disable":false,"appId":"test","createTime":"2020-11-26 03:21:43"}')
  return builder(v, '')
}
Mock.mock(new RegExp(URL.saveWebapp), 'post', saveWebapp)
