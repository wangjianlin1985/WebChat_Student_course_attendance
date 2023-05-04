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

  //提交服务器修改时段信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/timeInterval/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var timeIntervallist_page = pages[pages.length - 2];
      var timeIntervals = timeIntervallist_page.data.timeIntervals
      for(var i=0;i<timeIntervals.length;i++) {
        if(timeIntervals[i].intervalId == res.data.intervalId) {
          timeIntervals[i] = res.data
          break
        }
      }
      timeIntervallist_page.setData({timeIntervals:timeIntervals})
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

