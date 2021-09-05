import request from '@/utils/request'

/**
 * 树形查询
 * @param query
 * @returns {AxiosPromise}
 */
export function treeList(query) {
  return request({
    url: '/oneManage-system/resource/treeList',
    method: 'post',
    data: query
  })
}

/**
 * 树形查询下拉列表
 * @param query
 * @returns {AxiosPromise}
 */
export function treeSelect(query) {
  return request({
    url: '/oneManage-system/resource/treeSelect',
    method: 'post',
    data: query
  })
}

/**
 * 获取权限列表列表
 * @param query
 * @returns {AxiosPromise}
 */
export function treeAssignment(query) {
  return request({
    url: '/oneManage-system/resource/treeAssignment',
    method: 'post',
    data: query
  })
}

/**
 * 保存资源
 * @param query
 * @returns {AxiosPromise}
 */
export function resourceSave(query) {
  return request({
    url: '/oneManage-system/resource/save',
    method: 'post',
    data: query
  })
}

/**
 * 删除资源
 * @param query
 * @returns {AxiosPromise}
 */
export function resourceDelete(query) {
  return request({
    url: '/oneManage-system/resource/delete',
    method: 'post',
    data: query
  })
}
