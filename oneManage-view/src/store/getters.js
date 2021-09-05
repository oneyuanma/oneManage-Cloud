const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  lastLoginTime: state => state.user.lastLoginTime,
  name: state => state.user.name,
  lastLoginIp: state => state.user.lastLoginIp,
  roleNames: state => state.user.roleNames,
  roles: state => state.user.roles,
  menus: state => state.user.menus,
  permissions: state => state.user.permissions,
  permission_routes: state => state.permission.routes,
  errorLogs: state => state.errorLog.logs
}
export default getters
