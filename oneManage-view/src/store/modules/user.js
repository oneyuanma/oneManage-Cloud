import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import { Message } from 'element-ui'

const state = {
  // token
  token: getToken(),
  // 姓名
  name: '',
  // 上次登录时间
  lastLoginTime: '',
  // 上次登录ip
  lastLoginIp: '',
  // 角色名称
  roleNames: '',
  // 角色编码
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_LAST_LOGIN_IP: (state, lastLoginIp) => {
    state.lastLoginIp = lastLoginIp
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_LAST_LOGIN_TIME: (state, lastLoginTime) => {
    state.lastLoginTime = lastLoginTime
  },
  SET_ROLE_NAMES: (state, roleNames) => {
    state.roleNames = roleNames
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus
  },
  // 按钮级权限管控 - ②新增 mutations 操作 button
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(response => {
        const { data } = response
        console.log(data)
        commit('SET_TOKEN', data)
        setToken(data)
        Message({
          message: '登录成功',
          type: 'success',
          duration: 1 * 1000
        })
        resolve()
      }).catch(error => {
        console.info(error)
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          reject('验证失败，请重新登录。')
        }

        const { roles, username, lastLoginTime, lastLoginIp, menus, permissions, roleNames } = data

        // 角色必须是非空数组
        if (!roles || roles.length <= 0) {
          reject('当前用户没有角色权限，请联系管理员!')
        }

        if (menus && menus.length > 0) { // 验证返回的menus是否是一个非空数组
          commit('SET_MENUS', menus)
        }

        // 按钮级权限管控 - ④将取到的 button 权限放到全局变量中
        if (permissions && permissions.length > 0) { // 验证返回的buttons是否是一个非空数组
          commit('SET_PERMISSIONS', permissions)
        }

        commit('SET_ROLES', roles)
        commit('SET_NAME', username)
        commit('SET_LAST_LOGIN_TIME', lastLoginTime)
        commit('SET_LAST_LOGIN_IP', lastLoginIp)
        commit('SET_ROLE_NAMES', roleNames)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()

        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, { root: true })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const { roles } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
