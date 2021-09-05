import request from '@/utils/request'

export function pageList(query) {
  return request({
    url: '/oneManage-generate/generateDemo/pageList',
    method: 'post',
    data: query
  })
}

export function generateDemoSave(query) {
  return request({
    url: '/oneManage-generate/generateDemo/save',
    method: 'post',
    data: query
  })
}

export function generateDemoDelete(query) {
  return request({
    url: '/oneManage-generate/generateDemo/delete',
    method: 'post',
    data: query
  })
}

