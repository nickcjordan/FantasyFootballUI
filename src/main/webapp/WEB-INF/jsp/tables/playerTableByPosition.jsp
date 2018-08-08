<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="fill-body-wrapper">
	<div class="center"><h3><strong>${positionName}:</strong></h3></div>
		<table class="table table-sm table-striped header-fixed positions">
					<thead class="thead-inverse">
			<tr>
				<th class="id-posList">Rank</th>
				<th class="name-posList">Name</th>
				<th class="pos-posList">Pos</th>
				<th class="id-posList">Adp</th>
				<th class="id-posList">Team</th>
				<th class="id-posList">Bye</th>
				<th class="id-posList">Best</th>
				<th class="id-posList">Avg</th>
				<th class="id-posList">Worst</th>
				<th class="id-posList">St-Dv</th>
				<th class="id-posList">Vs. Adp</th>
				<th class="handcuff-posList">Backups</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${playerList}" var="player">
				<tr>
					<td class="id-posList">
						<a href="/pickPlayer?playerId=${player.id}">
				        	<span class="badge-adp-players">${player.rank}</span>
			      		</a>
					</td>
					<td class="name-posList"><a class="nameLink" data-toggle="modal" data-target="#${player.id}playerModal"><strong>${player.getNameAndTags()}</strong></a></td>
					<td class="pos-posList">
						<c:choose>
							<c:when test="${player.pos.equals('QB')}"><span class="badge badge-warning">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('RB')}"><span class="badge badge-info">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('WR')}"><span class="badge badge-success">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('TE')}"><span class="badge badge-error">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('K')}"><span class="badge">${player.pos} ${player.pos_rank}</span></c:when>
							<c:otherwise><span class="badge badge-inverse">${player.pos} ${player.pos_rank}</span></c:otherwise>
						</c:choose>
					</td>
					<td class="id-posList">${player.adp}</td>
					<td class="id-posList">${player.teamName}</td>
					<td class="id-posList">${player.bye}</td>
					<td class="id-posList">${player.best}</td>
					<td class="id-posList">${player.avg}</td>
					<td class="id-posList">${player.worst}</td>
					<td class="id-posList">${player.std_dev}</td>
					<td class="id-posList">${player.versus}</td>
					<td class="handcuff-posList">${player.checkForHandcuff()}</td>
				</tr>
				
				<%@include file="../common/modal.jsp"%>
				
			</c:forEach>
		</tbody>
		</table>
</div>


