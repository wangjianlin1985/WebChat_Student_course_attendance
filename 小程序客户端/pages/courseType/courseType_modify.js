var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    courseTypeId: 0, //课程类型id
    courseTypeName: "", //课程类型名称
    courseTypeDesc: "", //课程类型说明
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //提交服务器修改课程类型信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/courseType/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var courseTypelist_page = pages[pages.length - 2];
      var courseTypes = courseTypelist_page.data.courseTypes
      for(var i=0;i<courseTypes.length;i++) {
        if(courseTypes[i].courseTypeId == res.data.courseTypeId) {
          courseTypes[i] = res.data
          break
        }
      }
      courseTypelist_page.setData({courseTypes:courseTypes})
      setTimeout(function () {
        wx.navigateBack({})
      }, 500) 
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var courseTypeId = params.courseTypeId
    var url = config.basePath + "api/courseType/get/" + courseTypeId
    utils.sendRequest(url, {}, function (courseTypeRes) {
      wx.stopPullDownRefresh()
      self.setData({
        courseTypeId: courseTypeRes.data.courseTypeId,
        courseTypeName: courseTypeRes.data.courseTypeName,
        courseTypeDesc: courseTypeRes.data.courseTypeDesc,
      })

    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },

})

