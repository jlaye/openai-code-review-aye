curl -X POST \
        -H "Authorization: Bearer tokeneyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiZDQ5NDZlZWY2NzA4NGViMjkxNjJhNGM0ZjdjMjQ5NjEiLCJleHAiOjE3NzY4NjM3ODMyMzksInRpbWVzdGFtcCI6MTc3Njg2MTk4MzI4OH0.dd3A8PdX4zLVys_h9PpF8AST6wSnrcUU2_YVIgXBWww" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions