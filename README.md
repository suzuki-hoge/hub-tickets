# hub-tickets
operate github and zenhub api.

## api
### /attrs/labels ( GET )
+ fetch once when api server launch.

#### in
no parameter.

#### out
```json
[
  {
    "name": "dev - feature",
    "color": "c5def5"
  },
  {
    "name": "dev - refactor",
    "color": "ededed"
  }
]
```

### /attrs/assignees ( GET )
+ fetch once when api server launch, then filter by `board.json # presetAssignees`.

#### in
no parameter.

#### out
```json
[
  {
    "name": "suzuki-hoge",
    "icon": "https://avatars3.githubusercontent.com/u/18749992"
  }
]
```

### /attrs/pipelines ( GET )
+ fetch once when api server launch.

#### in
no parameter.

#### out
```json
[
  {
    "name": "backlog"
  },
  {
    "name": "sprint backlog"
  },
  {
    "name": "reviewing"
  }
]
```

### /command/issue/create ( POST )
+ create new issue.

#### in
key       | type   | constraint | default                       
:--       | :--    | :--        | :--                           
title     | string | require    | -                             
body      | string |            | no body                       
label     | string | require    | -                             
assignee  | string |            | no assignee                   
pipeline  | string |            | `board.json # defaultPipeline`
estimate  | float  | require    | -                             
milestone | -      | -          | latest milestone only         

#### out ( success )
```json
{
  "number": 123
}
```

#### out ( failure )
```json
{
  "error": "no such label"
}
```

### /command/issue/copy ( POST )
+ create new issue by copying existing issue.

key       | type   | constraint | default                       
:--       | :--    | :--        | :--                           
number    | int    | require    | -                             
title     | string | require    | -                             
body      | string |            | same body                     
label     | string |            | same label                    
assignee  | string |            | same assignee                 
pipeline  | string |            | same pipeline                 
estimate  | float  |            | same estimate                 
milestone | -      | -          | latest milestone only         

#### out ( success )
```json
{
  "number": 123
}
```

#### out ( failure )
```json
{
  "error": "no such label"
}
```

### /command/issue/cut ( POST )
+ create new issue by cutting existing issue, then comment `cut from #1` to new issue and comment `cut to #2 ( 5 sp )` to origin issue.
+ close origin issue if origin issue's estimate became zero.

key       | type   | constraint | default                       
:--       | :--    | :--        | :--                           
number    | int    | require    | -                             
title     | string | require    | -                             
body      | string |            | same body                     
label     | string |            | same label                    
assignee  | string |            | same assignee                 
pipeline  | string |            | same pipeline                 
estimate  | float  | require    | -                             
milestone | -      | -          | latest milestone only         

#### out ( success )
```json
{
  "number": 123
}
```

#### out ( failure )
```json
{
  "error": "no such label"
}
```

### /command/issue/assign ( POST )
+ assign issue.
+ if already assigned, do nothing.

key      | type   | constraint | default                       
:--      | :--    | :--        | :--                           
number   | int    | require    | -                             
assignee | string | require    | -

#### out ( success )
```json
{
}
```

#### out ( failure )
```json
{
  "error": "no such assignee"
}
```

### /command/milestone/create ( POST )
+ create new milestone.
+ if same name exists, do nothing.

key   | type       | constraint | default                       
:--   | :--        | :--        | :--                           
name  | string     | require    | -                             
start | yyyy-mm-dd | require    | -                             
end   | yyyy-mm-dd | require    | -

#### out ( success )
```json
{
  "result": "created"
}
```

or

```json
{
  "result": "already created ( no change )"
}
```

#### out ( failure )
```json
{
  "error": "date format is yyyy-mm-dd"
}
```

