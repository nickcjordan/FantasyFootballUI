<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-default strategy-panel">
  <div class="panel-heading strategy-header"><b>Current Round Strategy</b></div>
  <div class="panel-body strategy-body">${strategy.getStrategyText()}</div>

  <!-- List group -->
  <ul class="list-group">
   	<li class="list-group-item strategy-list-header"><b>Target Positions:</b></li>
   	<li class="list-group-item strategy-list-item">${strategy.getTargetPositions()}</li>
   	<li class="list-group-item strategy-list-header"><b>Target Players:</b></li>
    <c:forEach items="${strategy.getTargetPlayers()}" var="targetPlayer">
	    <li class="list-group-item strategy-list-item">${targetPlayer}</li>
    </c:forEach>
  </ul>
</div>
