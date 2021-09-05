import request from '@/utils/request'

/**
 * 登录日志分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function pageList(query) {
  return request({
    url: '/oneManage-log/log/login/pageList',
    method: 'post',
    data: query
  })
}

/**
 * 操作日志分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function operatePageList(query) {
  return request({
    url: '/oneManage-log/log/operate/pageList',
    method: 'post',
    data: query
  })
}
