<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<table class="table table-sm table-striped header-fixed dash">
		<thead class="thead-inverse">
			<tr>
				<th class="id-suggest">Adp</th>
				<th class="name-suggest">Name</th>
				<th class="pos-suggest">Pos</th>
				<th class="pos_rank-suggest">Rnk</th>
				<th class="team-suggest">Team</th>
				<th class="bye-suggest">Bye</th>
				<th class="stddev-suggest">
					<span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title=
					"How <b>unsure</b> people are of this player. This measures how much a set of values drifts from the <b>Average</b>, or <em>'a measure confidence'</em> in statistical conclusions">
													St-Dv</span><script> $('span').tooltip();</script></th>
				<th class="vsadp-suggest">
					<span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title=
					"How far away the player's <b>ADP</b> is from the player's <b>Overall Rank</b>">
													Vs.</span><script> $('span').tooltip();</script></th>
				<th class="handcuff-suggest">Backups</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${suggestions}" var="player">
			
				
				<tr class="tier${player.tier}">
					<td class="id-suggest">
						<a href="/pickPlayer?playerId=${player.id}">
				        	<span class="badge-adp">${player.adp}</span>
			      		</a>
					</td>
					<td class="name-suggest"><a class="nameLink" data-toggle="modal" data-target="#${player.id}playerModal"><strong>${player.getNameAndTags()}</strong></a></td>
					
					<c:choose>
						<c:when test="${player.pos.equals('QB')}">
							<td class="pos-suggest"><span class="badge badge-warning">${player.pos} ${player.pos_rank}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('RB')}">
							<td class="pos-suggest"><span class="badge badge-info">${player.pos} ${player.pos_rank}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('WR')}">
							<td class="pos-suggest"><span class="badge badge-success">${player.pos} ${player.pos_rank}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('TE')}">
							<td class="pos-suggest"><span class="badge badge-error">${player.pos} ${player.pos_rank}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('K')}">
							<td class="pos-suggest"><span class="badge">${player.pos} ${player.pos_rank}</span></td>
						</c:when>
						<c:otherwise>
							<td class="pos-suggest"><span class="badge badge-inverse">${player.pos} ${player.pos_rank}</span></td>
						</c:otherwise>
					</c:choose>
					
					<td class="pos_rank-suggest"><span class="badge-adp">${player.rank}</span></td>
					
					<td class="team-suggest">${player.teamName}</td>
					<td class="bye-suggest">${player.bye}</td>
					<td class="stddev-suggest">${player.std_dev}</td>
					<td class="vsadp-suggest">${player.versus}</td>
					<td class="handcuff-suggest">${player.checkForHandcuff()}</td>
				</tr>
				
				<%@include file="modal.jsp"%>
				
			</c:forEach>
		</tbody>
	</table>
</div>


