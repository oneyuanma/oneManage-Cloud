<template>
  <div>
    <iframe :src="address" id="mobsf" scrolling="no" frameborder="0" style=""></iframe>
  </div>
</template>

<script>
import {get} from '@/api/kv'

export default {
  data() {
    return {
      address: ''
    }
  },
  created() {
    // this.getAddress()
  },
  methods: {
    getAddress() {
      get({'k':'admin_address'}).then(response => {
        console.log(response)
        this.address = response.data
      })
    }
  },
  mounted() {
    this.getAddress()
    /**
     * iframe-宽高自适应显示
     */
    function changeMobsfIframe() {
      const mobsf = document.getElementById('mobsf')
      const deviceWidth = document.body.clientWidth
      const deviceHeight = document.body.clientHeight
      mobsf.style.width = (Number(deviceWidth) - 240) + 'px'
      mobsf.style.height = (Number(deviceHeight) - 88) + 'px'
    }

    changeMobsfIframe()

    window.onresize = function() {
      changeMobsfIframe()
    }
  }
}
</script>
