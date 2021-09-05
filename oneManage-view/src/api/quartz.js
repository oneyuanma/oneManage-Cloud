import request from '@/utils/request'

/**
 * 分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function pageList(query) {
  return request({
    url: '/oneManage-quartz/job/pageList',
    method: 'post',
    data: query
  })
}

/**
 * 保存定时任务
 * @param query
 * @returns {AxiosPromise}
 */
export function jobSave(query) {
  return request({
    url: '/oneManage-quartz/job/save',
    method: 'post',
    data: query
  })
}

/**
 * 删除定时任务
 * @param query
 * @returns {AxiosPromise}
 */
export function jobDelete(query) {
  return request({
    url: '/oneManage-quartz/job/delete',
    method: 'post',
    data: query
  })
}

/**
 * 定时任务状态设置
 * @param query
 * @returns {AxiosPromise}
 */
export function statusSet(query) {
  return request({
    url: '/oneManage-quartz/job/statusSet',
    method: 'post',
    data: query
  })
}

/**
 * 定时任务启动
 * @param query
 * @returns {AxiosPromise}
 */
export function run(query) {
  return request({
    url: '/oneManage-quartz/job/run',
    method: 'post',
    data: query
  })
}
