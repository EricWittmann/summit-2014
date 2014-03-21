
SVGElement.prototype.hasClass = function (className) {
  return new RegExp('(\\s|^)' + className + '(\\s|$)').test(this.getAttribute('class'));
};

SVGElement.prototype.addClass = function (className) {
  if (!this.hasClass(className)) {
    this.setAttribute('class', this.getAttribute('class') + ' ' + className);
  }
};

SVGElement.prototype.removeClass = function (className) {
  var removedClass = this.getAttribute('class').replace(new RegExp('(\\s|^)' + className + '(\\s|$)', 'g'), '$2');
  if (this.hasClass(className)) {
    this.setAttribute('class', removedClass);
  }
};

SVGElement.prototype.toggleClass = function (className) {
  if (this.hasClass(className)) {
    this.removeClass(className);
  } else {
    this.addClass(className);
  }
};

var w = 1024, h = 768, node, link, force, vis;
var i = 0;

function update(root) {
	var nodes = flatten(root);
	var links = d3.layout.tree().links(nodes);

	// Restart the force layout.
	force.nodes(nodes).links(links).start();

	// Update the links...
	link = vis.selectAll("line.link").data(links, function(d) {
		return d.target.id;
	});

	// Enter any new links.
	link.enter().insert("svg:line", ".node").attr("class", getLinkClass)
	    .attr("id", getLinkId)
		.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

	// Remove any old links.
	link.exit().remove();

	// Update the nodes...
	node = vis.selectAll(".node").data(nodes, function(d) {
		return d.id;
	});

	// Enter any new nodes.	
	node.enter().append("svg:g").attr("class", getNodeClass).attr("id", getNodeId).call(force.drag);
	node.html('');
	node.append(createNodeShape)
		.attr("cx", function(d) { return 0; })
		.attr("cy", function(d) { return 0; })
		.attr("r", function(d) { return nodeSize(d); });

	node.append("svg:text")
		.attr("dx", function(d) { return nodeSize(d) + 5; })
		.attr("dy", ".35em")
		.attr("class", "node-label")
		.text(function(d) { return d.type == 'profile' ? d.name : ''; });

	// Remove any old nodes.
	node.exit().remove();
}

function getLinkId(d) {
	return 'link-' + d.target.id;
}

function getNodeId(d) {
	return 'node-' + d.id;
}

function getNodeClass(d) {
	return 'node node-' + d.type + (d.active ? ' node-active' : '');
}

function getLinkClass(d) {
	return 'link link-to-' + d.target.type + (d.target.active ? ' link-active' : '');
}

function createNodeShape(d) {
	var shape;
	if (d.type == 'root') {
		shape = this.ownerDocument.createElementNS("http://www.w3.org/2000/svg", "rect");
		shape.setAttribute('x', -20);
		shape.setAttribute('y', -20);
		shape.setAttribute('width', 40);
		shape.setAttribute('height', 40);
	} else {
		shape = this.ownerDocument.createElementNS("http://www.w3.org/2000/svg", "circle");
		shape.setAttribute('cx', 0);
		shape.setAttribute('cy', 0);
		shape.setAttribute('r', nodeSize(d));
	}
	return shape;
}

function nodeSize(d) {
	if (d.type == 'root') {
		return 30;
	}
	if (d.type == 'profile') {
		return 25;
	}
	return d.size;
}

function tick() {
	link.attr("x1", function(d) {
		return d.source.x;
	}).attr("y1", function(d) {
		return d.source.y;
	}).attr("x2", function(d) {
		return d.target.x;
	}).attr("y2", function(d) {
		return d.target.y;
	});

    node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
}

// Color leaf nodes orange, and packages white or blue.
function color(d) {
	return d.children ? "#c6dbef" : "#429cd5";
}

// Returns a list of all nodes under the root.
function flatten(root) {
	var nodes = [];

	function nodeSize(node) {
		if (node.children)
			node.size = node.children.reduce(function(p, v) {
				return p + nodeSize(v);
			}, 0);
		if (!node.id)
			node.id = ++i;
		nodes.push(node);
		return node.size;
	}

	root.size = nodeSize(root);
	return nodes;
}


function activateGear(whichGear) {
    var gearId = 'node-' + whichGear;
    var linkId = 'link-' + whichGear;
    var gear = document.getElementById(gearId);
    var link = document.getElementById(linkId);
    if (gear && link) {
	    gear.addClass('node-active');
	    gear.addClass('pulse');
	    link.addClass('link-active');
	    link.addClass('pulse');
	    setTimeout(function () {
	        gear.removeClass('pulse');
	        link.removeClass('pulse');
	    }, 5600);
    }
}


function deactivateGear(whichGear) {
    var gearId = 'node-' + whichGear;
    var linkId = 'link-' + whichGear;
    var gear = document.getElementById(gearId);
    var link = document.getElementById(linkId);
    if (gear && link) {
	    gear.removeClass('node-active');
	    gear.addClass('pulse');
	    link.removeClass('link-active');
	    link.addClass('pulse');
	    setTimeout(function () {
	        gear.removeClass('pulse');
	        link.removeClass('pulse');
	    }, 5600);
    }
}

$(document).ready(
		function() {
			force = d3.layout.force().on("tick", tick).charge(function(d) {
				if (d.type == 'root') {
					return -1000;
				}
				if (d.type == 'profile') {
					return -1000;
				}
				if (d.type == 'gear') {
					return -900;
				}
			}).linkDistance(function(d) {
				return d.target.type == 'gear' ? 40 : 150;
			}).size([ w, h - 160 ]);

			vis = d3.select(".graph").append("svg:svg").attr("width", w)
					.attr("height", h);
		}
);
