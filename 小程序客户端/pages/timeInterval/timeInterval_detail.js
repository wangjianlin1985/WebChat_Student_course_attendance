var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    intervalId: 0, //时段id
    intervalName: "", //时段名称
    product: "", //商品数量
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var intervalId = params.intervalId
    var url = config.basePath + "api/timeInterval/get/" + intervalId
    utils.sendRequest(url, {}, function (timeIntervalRes) {
      wx.stopPullDownRefresh()
      self.setData({
        intervalId: timeIntervalRes.data.intervalId,
        intervalName: timeIntervalRes.data.intervalName,
        product: timeIntervalRes.data.product,
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

