import request from '@/utils/request'

/**
 * 分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function pageList(query) {
  return request({
    url: '/oneManage-generate/table/pageList',
    method: 'post',
    data: query
  })
}

/**
 * 保存表信息
 * @param query
 * @returns {AxiosPromise}
 */
export function tableSave(query) {
  return request({
    url: '/oneManage-generate/table/save',
    method: 'post',
    data: query
  })
}

/**
 * 删除表信息
 * @param query
 * @returns {AxiosPromise}
 */
export function tableDelete(query) {
  return request({
    url: '/oneManage-generate/table/delete',
    method: 'post',
    data: query
  })
}

/**
 * 表同步
 * @param query
 * @returns {AxiosPromise}
 */
export function tableSync(query) {
  return request({
    url: '/oneManage-generate/table/sync',
    method: 'post',
    data: query
  })
}

/**
 * 字段信息详情
 * @param query
 * @returns {AxiosPromise}
 */
export function fieldDetail(query) {
  return request({
    url: '/oneManage-generate/field/detail',
    method: 'post',
    data: query
  })
}

/**
 * 保存字段信息
 * @param query
 * @returns {AxiosPromise}
 */
export function fieldSave(query) {
  return request({
    url: '/oneManage-generate/field/save',
    method: 'post',
    data: query
  })
}

/**
 * 代码生成
 * @param query
 * @returns {AxiosPromise}
 */
export function generate(query) {
  return request({
    url: '/oneManage-generate/table/generate',
    method: 'post',
    data: query
  })
}

/**
 * 所有表名下拉选项
 * @param query
 * @returns {AxiosPromise}
 */
export function selectOptions(query) {
  return request({
    url: '/oneManage-generate/table/selectOptions',
    method: 'post',
    data: query
  })
}
