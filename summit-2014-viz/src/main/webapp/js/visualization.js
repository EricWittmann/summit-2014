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
	link.enter().insert("svg:line", ".node").attr("class", "link")
		.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

	// Remove any old links.
	link.exit().remove();

	// Update the nodes...
	node = vis.selectAll(".node").data(nodes, function(d) {
		return d.id;
	}).style("fill", color);

//	node.transition().attr("r", function(d) {
//		return d.children ? 25 : d.size;
//	});

	// Enter any new nodes.	
	node.enter().append("svg:g").attr("class", "node").call(force.drag);
	node.html('');
	node.append(createNodeShape)
		.attr("cx", function(d) { return 0; })
		.attr("cy", function(d) { return 0; })
		.attr("r", function(d) { return nodeSize(d); })
		.style("fill", color).on("click", click);

	node.append("svg:text")
		.attr("dx", function(d) { return nodeSize(d) + 5; })
		.attr("dy", ".35em")
		.attr("class", "node-label")
		.text(function(d) { return d.name == 'root' ? '' : d.name; });

	// Remove any old nodes.
	node.exit().remove();
}

function createNodeShape(d) {
	var shape;
	if (d.type == 'root') {
		shape = this.ownerDocument.createElementNS("http://www.w3.org/2000/svg", "rect");
		shape.setAttribute('x', 0);
		shape.setAttribute('y', 0);
		shape.setAttribute('width', 0);
		shape.setAttribute('height', 0);
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
		if (isCollapsed(d)) {
			return 15;
		} else {
			return 25;
		}
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
	return isCollapsed(d) ? "#f1ac6f" : d.children ? "#c6dbef" : "#429cd5";
}

// Toggle children on click.
function click(d) {
	if (d.children) {
		d._children = d.children;
		d.children = null;
	} else {
		d.children = d._children;
		d._children = null;
	}
	update();
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

function isCollapsed(d) {
	return d._children;
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
