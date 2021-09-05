import request from '@/utils/request'

export function pageList(query) {
  return request({
    url: '/oneManage-${mouldName}/${classNameParam}/pageList',
    method: 'post',
    data: query
  })
}

export function ${classNameParam}Save(query) {
  return request({
    url: '/oneManage-${mouldName}/${classNameParam}/save',
    method: 'post',
    data: query
  })
}

export function ${classNameParam}Delete(query) {
  return request({
    url: '/oneManage-${mouldName}/${classNameParam}/delete',
    method: 'post',
    data: query
  })
}

