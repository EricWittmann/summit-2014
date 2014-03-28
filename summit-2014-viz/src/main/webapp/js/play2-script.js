var counter = 0;
var numSteps = 0;
var data = {
      "name": "root",
      "type" : "root",
      "children": [
        {
          "name": "compute1",
          "type" : "profile",
          "children": [
                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
          ]
        },
        {
          "name": "compute2",
          "type" : "profile",
          "children": [
                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
          ]
        }
      ]
    };


var updates = [
    {
	      "name": "root",
	      "type" : "root",
	      "children": [
	        {
	          "name": "compute1",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	          "name": "compute2",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	              "name": "compute3",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        }

        ]
    },

    {
	      "name": "root",
	      "type" : "root",
	      "children": [
	        {
	          "name": "compute1",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	          "name": "compute2",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	              "name": "compute3",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        },
	        {
	              "name": "compute4",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        }
        ]
    },

    
    {
	      "name": "root",
	      "type" : "root",
	      "children": [
	        {
	          "name": "compute1",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	          "name": "compute2",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	              "name": "compute3",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        },
	        {
	              "name": "compute4",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        }
        ]
    },

    
    {
	      "name": "root",
	      "type" : "root",
	      "children": [
	        {
	          "name": "compute1",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	              "name": "compute3",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        },
	        {
	              "name": "compute4",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false },
	                           { "name": "gear-3", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-4", "type" : "gear", "size" : 12, "active" : false }
	              ]
	        }
        ]
    },

    
    {
	      "name": "root",
	      "type" : "root",
	      "children": [
	        {
	          "name": "compute1",
	          "type" : "profile",
	          "children": [
	                       { "name": "gear-1", "type" : "gear", "size" : 12, "active" : false },
	                       { "name": "gear-2", "type" : "gear", "size" : 12, "active" : false }
	          ]
	        },
	        {
	              "name": "compute3",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : true }
	              ]
	        },
	        {
	              "name": "compute4",
	              "type" : "profile",
	              "children": [
	                           { "name": "gear-1", "type" : "gear", "size" : 12, "active" : true },
	                           { "name": "gear-2", "type" : "gear", "size" : 12, "active" : true }
	              ]
	        }
        ]
    }


];

function runDemo() {
	var u = updates;
	counter = 0;
	numSteps = u.length;
	demo_step();
}

function demo_step() {
	if (counter >= numSteps) {
		return;
	}
	
	mergeInto(updates[counter], data);
	update(data);
	counter = counter + 1;
    window.setTimeout(demo_step, 2000);
}
