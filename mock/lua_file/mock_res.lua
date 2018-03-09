local uri = ngx.var.request_uri
local content_type = "application/json"
local param = ngx.req.get_uri_args()
local sleep_time = 0

local fo = io.open("/usr/local/openresty/nginx/lua_file/data.json", "rb")
local content = fo:read("*all")
fo:close()

if param['time'] then
  sleep_time = param['time']
  ngx.sleep(sleep_time)
end

ngx.header["Content-Length"] = #content
ngx.header["Content-Type"] = content_type
ngx.header["Connection"] = "keep-alive"

ngx.status = 200
ngx.say(content)
