import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const permissions = store.getters && store.getters.permissions
    const permissionRoles = value

    const hasPermission = permissions.some(perminssion => {
      return permissionRoles.includes(perminssion)
    })
    return hasPermission
  } else {
    console.error(`need roles! Like v-permission="['admin']"`)
    return false
  }
}
