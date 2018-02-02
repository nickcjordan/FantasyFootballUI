<%@page import="fantasy.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<div class="center"><h3><strong>Draft Board:</strong></h3></div>
		<table class="table table-sm table-striped header-fixed positions">
			<thead class="thead-inverse">
				<tr>
					<th class="drafted-id">#</th>
					<c:forEach items="${draft.getOrderedNames()}" var="drafter">
						<th class="drafted-name">${drafter}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach var = "i" begin = "0" end = "${roundNumber - 1}">
					<tr>
						<td class="drafted-id2"><strong>${i+1}</strong></td>
						<c:forEach items="${drafters}" var="drafter">
						      
							<c:choose>
							    <c:when test="${drafter.getDraftedTeam().getAllInDraftedOrder().size() > i }">
									<c:set var="player" value="${drafter.getDraftedTeam().getAllInDraftedOrder().get(i)}" scope="page"/>
							    	<td class="drafted"><a class="nameLink" data-toggle="modal" data-target="#${drafter.getDraftedTeam().getAllInDraftedOrder().get(i).id}playerModal">${drafter.getDraftedTeam().getAllInDraftedOrder().get(i).getNameAndId()}</a></td>
							    </c:when>    
							    <c:otherwise>
									<td class="drafted"> </td>
							    </c:otherwise>
							</c:choose>
						
						<%@include file="modal.jsp"%>
				    	</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
