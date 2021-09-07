import request from '@/utils/request'

/**
 * 分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function pageList(query) {
  return request({
    url: '/oneManage-system/kv/pageList',
    method: 'post',
    data: query
  })
}

/**
 * 保存键值对
 * @param query
 * @returns {AxiosPromise}
 */
export function kvSave(query) {
  return request({
    url: '/oneManage-system/kv/save',
    method: 'post',
    data: query
  })
}

/**
 * 删除键值对
 * @param query
 * @returns {AxiosPromise}
 */
export function kvDelete(query) {
  return request({
    url: '/oneManage-system/kv/delete',
    method: 'post',
    data: query
  })
}

/**
 * 根据键获取值
 * @param query
 * @returns {AxiosPromise}
 */
export function get(query) {
  return request({
    url: '/oneManage-system/kv/get',
    method: 'post',
    data: query
  })
}
