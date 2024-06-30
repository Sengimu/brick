import request from "@/utils/request.js";

export const getBingImageInfo = async () => {
  const {images: [{url}]} = await request.get('/bing');
  return 'https://cn.bing.com' + url
}

export const getYiYan = async () => {
  const {hitokoto} = await request.get('/yi/?c=d&c=i&c=k');
  return hitokoto
}
