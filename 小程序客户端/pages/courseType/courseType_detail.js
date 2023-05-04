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
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  }

})

