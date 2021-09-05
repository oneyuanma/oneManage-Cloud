import request from '@/utils/request'

/**
 * 登录
 * @param data
 * @returns {AxiosPromise}
 */
export function login(data) {
  return request({
    url: '/oneManage-auth/auth/login',
    method: 'post',
    data: data
  })
}

/**
 * 获取用户信息
 * @returns {AxiosPromise}
 */
export function getInfo() {
  return request({
    url: '/oneManage-system/user/getUserInfo',
    method: 'post',
    data: {}
  })
}

/**
 * 退出
 * @returns {AxiosPromise}
 */
export function logout() {
  return request({
    url: '/oneManage-auth/auth/logOut',
    method: 'post'
  })
}

/**
 * 分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function fetchList(query) {
  return request({
    url: '/oneManage-system/user/pageList',
    method: 'post',
    data: query
  })
}

/**
 * 保存用户
 * @param query
 * @returns {AxiosPromise}
 */
export function userSave(query) {
  return request({
    url: '/oneManage-system/user/save',
    method: 'post',
    data: query
  })
}

/**
 * 删除用户
 * @param query
 * @returns {AxiosPromise}
 */
export function userDelete(query) {
  return request({
    url: '/oneManage-system/user/delete',
    method: 'post',
    data: query
  })
}

/**
 * 用户状态设置
 * @param query
 * @returns {AxiosPromise}
 */
export function userSet(query) {
  return request({
    url: '/oneManage-system/user/set',
    method: 'post',
    data: query
  })
}
