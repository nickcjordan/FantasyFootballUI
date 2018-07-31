<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<tr>
		<td class="id-3">
			<a href="/pickPlayer?playerId=${player.id}">
	        	<span class="badge-adp-players">${player.adp}</span>
      		</a>
		</td>
		<td class="name-3"><a class="nameLink" data-toggle="modal" data-target="#${player.id}playerModal">${player.getNameAndTags()}</a></td>
		<td class="pos_rank-3">${player.pos_rank}/${player.rank}</td>
		<td class="team-3">${player.teamName}</td>
		<td class="bye-3">${player.bye}</td>
		<td class="handcuff-3">${player.checkForHandcuff()}</td>
	</tr>

	<%@include file="modal.jsp"%>