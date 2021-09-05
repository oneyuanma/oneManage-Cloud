import request from '@/utils/request'

/**
 * 角色下拉选项集合
 * @param query
 * @returns {AxiosPromise}
 */
export function selectOptions(query) {
  return request({
    url: '/oneManage-system/role/selectOptions',
    method: 'post',
    data: query
  })
}

/**
 * 分页查询
 * @param query
 * @returns {AxiosPromise}
 */
export function fetchList(query) {
  return request({
    url: '/oneManage-system/role/pageList',
    method: 'post',
    data: query
  })
}

/**
 * 获取角色资源管理关系
 * @param query
 * @returns {AxiosPromise}
 */
export function getRoleResource(query) {
  return request({
    url: '/oneManage-system/role/getRoleResource',
    method: 'post',
    data: query
  })
}

/**
 * 角色保存
 * @param query
 * @returns {AxiosPromise}
 */
export function roleSave(query) {
  return request({
    url: '/oneManage-system/role/save',
    method: 'post',
    data: query
  })
}

/**
 * 保存角色资源关联关系
 * @param query
 * @returns {AxiosPromise}
 */
export function saveRoleResource(query) {
  return request({
    url: '/oneManage-system/role/saveRoleResource',
    method: 'post',
    data: query
  })
}

/**
 * 角色删除
 * @param query
 * @returns {AxiosPromise}
 */
export function roleDelete(query) {
  return request({
    url: '/oneManage-system/role/delete',
    method: 'post',
    data: query
  })
}
