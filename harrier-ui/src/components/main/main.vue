<template>
  <Layout style="height: 100%" class="main">
    <Header class="header-con"  >
      <!-- 大数据中心实时数据仓库批量监控系统 -->
      <!-- <div class="sys-bar-logo">
        <Row class="sys-bar-logo-row" style="width:200px">
          <Col span="14" class="sys-bar-logo-row" style="width:70px">
            <img src="../../../static/img/logobg-white.png" />
          </Col>
          <!-- <Col span="8">
            <Row >
              <div class="logoTitle_m1">浦发银行</div>
              <div class="logoTitle_m2">SPD BANK</div>
            </Row>
          </Col>
        </Row>
      </div> -->
      <!--
      <div class="sys-bar-logoline"  style="width:20px">
          <img src="../../../static/img/logoline.png" />
        </div>-->
      <div class="sys-bar-title" >
        <span>{{ homeTitle }}</span>
      </div>

      <header-bar :collapsed="collapsed" @on-coll-change="handleCollapsedChange">
        <!-- <user :message-unread-count="unreadCount" :user-avatar="minLogo"/> -->
        <user :message-unread-count="unreadCount" :user-avatar="userAvatar"/>
        <help style="margin-right:4%"/>
        &nbsp;&nbsp;
        <fullscreen v-model="isFullscreen" style="margin-right: 20px;"/>
      </header-bar>

    </Header>
    <Layout>
      <Sider hide-trigger collapsible :width="220" :collapsed-width="64" v-model="collapsed" class="left-sider" :style="{overflow: 'hidden'}">
        <side-menu
          accordion ref="sideMenu"
          :active-name="$route.name"
          :collapsed="collapsed"
          @on-select="turnToPage"
          :menu-list="menuList">
          <!-- 需要放在菜单上面的内容，如Logo，写在side-menu标签内部，如下 -->
         <!--
          <div class="logo-con">
            <img style="width:100%;height:100%" v-show="!collapsed" :src="maxLogo" key="max-logo" />
            <img style="width:65%;height:75%" v-show="collapsed" :src="minLogo" key="min-logo" />
          </div> -->

        </side-menu>
      </Sider>
      <Content class="main-content-con">
        <Layout class="main-layout-con">
         <Row type="flex" justify="center" >
            <!-- <Col style="float:left;width:3%;z-index:20;background-color:#fff">
              <div style="padding:3px 0 0 6px">
              <sider-trigger :collapsed="collapsed" icon="md-menu" @on-change="handleCollapsedChange"></sider-trigger>
              </div>
            </Col> -->
            <Col style="width:97%">
              <div class="tag-nav-wrapper">
                <tags-nav :value="$route" @input="handleClick" :list="tagNavList" @on-close="handleCloseTag"/>
              </div>
              </Col>
          </Row>
          <!--
          <div class="tag-nav-wrapper">
            <tags-nav :value="$route" @input="handleClick" :list="tagNavList" @on-close="handleCloseTag"/>
          </div>-->
          <Content class="content-wrapper">
            <keep-alive :include="cacheList">
              <router-view/>
            </keep-alive>
            <ABackTop :height="100" :bottom="80" :right="50" container=".content-wrapper"></ABackTop>
          </Content>
          <Footer id="main-footer" >
            <h5>2019-2021&copy;{{ homeTitle }}</h5>
          </Footer>
        </Layout>
      </Content>
    </Layout>
  </Layout>
</template>
<script>
import SideMenu from './components/side-menu'
import siderTrigger from './components/header-bar/sider-trigger'
import HeaderBar from './components/header-bar'
import TagsNav from './components/tags-nav'
import User from './components/user'
import Help from './components/help'
import ABackTop from './components/a-back-top'
import Fullscreen from './components/fullscreen'
import Language from './components/language'
import ErrorStore from './components/error-store'
import { mapMutations, mapActions, mapGetters } from 'vuex'
import { getNewTagList, routeEqual } from '@/libs/util'
import routers from '@/router/routers'
import minLogo from '@/assets/images/logoSmall.png'
import maxLogo from '@/assets/images/logoBig.png'
import './main.less'
export default {
  name: 'Main',
  components: {
    siderTrigger,
    SideMenu,
    HeaderBar,
    Language,
    TagsNav,
    Fullscreen,
    ErrorStore,
    User,
    Help,
    ABackTop
  },
  data () {
    return {
      homeTitle: 'Harrier',
      collapsed: false,
      minLogo,
      maxLogo,
      isFullscreen: false
    }
  },
  computed: {
    ...mapGetters([
      'errorCount'
    ]),
    tagNavList () {
      return this.$store.state.app.tagNavList
    },
    tagRouter () {
      return this.$store.state.app.tagRouter
    },
    userAvatar () {
      return this.$store.state.user.avatarImgPath
    },
    cacheList () {
      const list = ['ParentView', ...this.tagNavList.length ? this.tagNavList.filter(item => !(item.meta && item.meta.notCache)).map(item => item.name) : []]
      return list
    },
    menuList () {
      return this.$store.getters.menuList
    },
    local () {
      return this.$store.state.app.local
    },
    hasReadErrorPage () {
      return this.$store.state.app.hasReadErrorPage
    },
    unreadCount () {
      return this.$store.state.user.unreadCount
    }
  },
  methods: {
    ...mapMutations([
      'setBreadCrumb',
      'setTagNavList',
      'addTag',
      'setLocal',
      'setHomeRoute',
      'closeTag'
    ]),
    ...mapActions([
      'handleLogin',
      'getUnreadMessageCount'
    ]),
    turnToPage (route) {
      let { name, params, query } = {}
      if (typeof route === 'string') name = route
      else {
        name = route.name
        params = route.params
        query = route.query
      }
      if (name.indexOf('isTurnByHref_') > -1) {
        window.open(name.split('_')[1])
        return
      }
      this.$router.push({
        name,
        params,
        query
      })
    },
    handleCollapsedChange (state) {
      this.collapsed = state
    },
    handleCloseTag (res, type, route) {
      if (type !== 'others') {
        if (type === 'all') {
          this.turnToPage(this.$config.homeName)
        } else {
          if (routeEqual(this.$route, route)) {
            this.closeTag(route)
          }
        }
      }
      this.setTagNavList(res)
    },
    handleClick (item) {
      this.turnToPage(item)
    }
  },
  watch: {
    '$route' (newRoute) {
      const { name, query, params, meta } = newRoute
      this.addTag({
        route: { name, query, params, meta },
        type: 'push'
      })
      this.setBreadCrumb(newRoute)
      this.setTagNavList(getNewTagList(this.tagNavList, newRoute))
      this.$refs.sideMenu.updateOpenName(newRoute.name)
    }
  },
  mounted () {
    /**
     * @description 初始化设置面包屑导航和标签导航
     */
    this.setTagNavList()
    // this.setHomeRoute(this.$store.getters.menues)
    this.setHomeRoute(routers)
    const { name, params, query, meta } = this.$route
    this.addTag({
      route: { name, params, query, meta }
    })
    this.setBreadCrumb(this.$route)
    // 设置初始语言
    this.setLocal(this.$i18n.locale)
    // 如果当前打开页面不在标签栏中，跳到homeName页
    if (!this.tagNavList.find(item => item.name === this.$route.name)) {
      this.$router.push({
        name: this.$config.homeName
      })
    }
    // 获取未读消息条数
    this.getUnreadMessageCount()
  }
}
</script>
