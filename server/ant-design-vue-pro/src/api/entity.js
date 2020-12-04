import request from '@/utils/request'
import Qs from 'qs'

const api = {
  save: '/admin/save',
  list: '/admin/entity.list',
  delete: '/admin/delete'
}

export default api

// webapp
export function listWebapp (parameter) {
  return request({
    url: api.list,
    method: 'post',
    data: {
      ...parameter,
      entity: 'Webapp'
    }
  })
}
export function saveWebapp (parameter) {
  return request({
    url: api.save + '/Webapp',
    method: 'post',
    data: {
      ...parameter
    }
  })
}
export function deleteWebapp (parameter) {
  return request({
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    url: api.delete + '/Webapp',
    method: 'post',
    data: Qs.stringify(parameter)
  })
}

// role
export function listRole (parameter) {
  return request({
    url: api.list,
    method: 'post',
    data: {
      ...parameter,
      entity: 'Role'
    }
  })
}
export function saveRole (parameter) {
  return request({
    url: api.save + '/Role',
    method: 'post',
    data: {
      ...parameter
    }
  })
}
export function deleteRole (parameter) {
  return request({
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    url: api.delete + '/Role',
    method: 'post',
    data: Qs.stringify(parameter)
  })
}

// account
export function listAccount (parameter) {
  return request({
    url: api.list,
    method: 'post',
    data: {
      ...parameter,
      entity: 'Account'
    }
  })
}
export function saveAccount (parameter) {
  return request({
    url: api.save + '/Account',
    method: 'post',
    data: {
      ...parameter
    }
  })
}
export function deleteAccount (parameter) {
  return request({
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    url: api.delete + '/Account',
    method: 'post',
    data: Qs.stringify(parameter)
  })
}

// permission
export function listPermission (parameter) {
  return request({
    url: api.list,
    method: 'post',
    data: {
      ...parameter,
      entity: 'Authority'
    }
  })
}
export function savePermission (parameter) {
  return request({
    url: api.save + '/Authority',
    method: 'post',
    data: {
      ...parameter
    }
  })
}
export function deletePermission (parameter) {
  return request({
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    url: api.delete + '/Authority',
    method: 'post',
    data: Qs.stringify(parameter)
  })
}

export function trimObject (parameter, deleteBlank = true) {
  for (const k in parameter) {
    let v = parameter[k]
    if (typeof v === 'string') {
      v = v.trim()
      parameter[k] = v
      if (deleteBlank && v.length === 0) {
        delete parameter[k]
      }
    }
    return parameter
  }
}
